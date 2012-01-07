package SJmp3.video;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import net.sourceforge.parser.mkv.Utils;
import net.sourceforge.parser.mkv.type.BlockGroup;
import net.sourceforge.parser.mkv.type.Cluster;
import net.sourceforge.parser.mkv.type.Master;
import net.sourceforge.parser.mkv.type.TrackEntry;
import net.sourceforge.parser.util.ByteStream;
import net.sourceforge.parser.util.TreeNode;

public class MkvSampleReader extends Thread
{
	private boolean release_flag = false;
	private BlockingQueue<byte[]> videoSampleQueue;
	private MkvParserWrapper wrapper;
	
	private PipedInputStream audio_stream;
	private PipedOutputStream audio_out;
	
	private TreeNode currentVideoTrack;
	private TreeNode currentAudioTrack;
	
	public MkvSampleReader(MkvParserWrapper wrapper, BlockingQueue<byte[]> videoSampleQueue) throws IOException
	{
		this(wrapper, videoSampleQueue, null);
	}
	
	public MkvSampleReader(MkvParserWrapper wrapper, BlockingQueue<byte[]> videoSampleQueue, InputStream audio_stream) throws IOException
	{
		this.wrapper = wrapper;
		this.videoSampleQueue = videoSampleQueue;
		
		currentVideoTrack = wrapper.getVideoTrack(0);
		currentAudioTrack = wrapper.getAudioTrack(0);
		
		if (audio_stream != null)
		{
			this.audio_out = new PipedOutputStream((PipedInputStream)audio_stream);
			this.audio_stream = (PipedInputStream)audio_stream;
		}
		start();
	}
	
	public void run()
	{
		while(!release_flag)
		{
			try {
				readSamples();
				release_flag = true;
			} catch (Exception e)
			{
				e.printStackTrace();
				release_flag = true;
			}
		}
	}
	
	public void release()
	{
		release_flag = true;
		try
		{
			if (videoSampleQueue.remainingCapacity() == 0)
				videoSampleQueue.take();
			videoSampleQueue.clear();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	public void readSamples() throws IOException, InterruptedException
	{
		TreeNode segment = wrapper.findMaster("Segment", wrapper.getRoot());
		Cluster cluster = (Cluster)wrapper.findMaster("Cluster", segment);
		ByteStream stream = wrapper.getParser().getByteStream();
		
		stream.position(cluster.position);
		
		int b = 0;
		int next = 0;
		while ((b = stream.read()) != -1)
		{
			if (release_flag) return;
			
			next <<= 8;
			next |= (0xff & b);
			long t4 = (next & 0xffffffff);
			
			if (t4 == 0x1F43B675L)
			{
				long pos = stream.position()-4;
				long size = Utils.getSize(stream);
				cluster = new Cluster(t4, size, pos);
				
				readSegment(cluster, stream);
			}
		}
	}
	
	private void readSegment(Master master, ByteStream stream) throws IOException, InterruptedException
	{
		long remaining = master.size;
		
		while (remaining > 0)
		{
			if (release_flag) {remaining = 0; return;}
			
			long type = stream.read();
			
			long size = Utils.getSize(stream);
			remaining -= size + 1 + size_octets(size);
			
			if (type == 0xa0) // block group
			{
				 Master node = new BlockGroup(type, size, -1);
				 readSegment(node, stream);
			} else if (type == 0xa3) // simple block
			{
				addBlock(stream, size);
			} else
			{
				if (type == 0xa1) // block
				{
					addBlock(stream, size);
				} else
				{
					stream.skip(size);
				}
			}
		}
	}
	
	public static final int NO_LACING = 0;
	public static final int XIPH_LACING = 1;
	public static final int EBML_LACING = 3;
	public static final int FIXED_SIZE_LACING = 2;
	
	private void addBlock(ByteStream stream, long size) throws IOException, InterruptedException
	{
		int track_number = (stream.read() & 0xff) ^128;
		long time_code = Utils.bytesToLong(stream, 2);
		int flags = stream.read();
		int lacing = (flags & 6) >> 1;
		size -= 4;
		
		int cvtn = ((TrackEntry)currentVideoTrack).TrackNumber;
		int catn = ((TrackEntry)currentAudioTrack).TrackNumber;
			
		int num_of_frames = 1;
		int frames_size[] = null;
			
		switch (lacing)
		{
		case FIXED_SIZE_LACING:
			num_of_frames = stream.read() + 1;
			size--;
			break;
		case EBML_LACING:
			num_of_frames = stream.read() + 1;
			size--;
			frames_size = ebmlLacing(num_of_frames, size, stream);
			break;
		case XIPH_LACING:
			break;
		case NO_LACING:
			break;
		}
		
		if (track_number == cvtn)
		{
			byte data[] = new byte[(int)size];
			stream.read(data);
			//VideoSample sample = new VideoSample(data, ((TrackEntry)currentVideoTrack).CodecPrivate, 0);
			videoSampleQueue.offer(data, 1<<25, TimeUnit.MILLISECONDS);
		} else if (track_number == catn)
		{
			if (lacing == EBML_LACING)
			{
				for (int i = 0; i < frames_size.length; i++)
				{
					byte data[] = new byte[frames_size[i]];
					stream.read(data);
					
					if (audio_stream != null)
						audio_out.write(data);
				}
			} else
			{
				byte data[] = new byte[(int)size];
				stream.read(data);
					
				if (audio_stream != null)
					audio_out.write(data);
			}
		} else
		{
			stream.skip(size);
		}
	}
	
	private int[] ebmlLacing(int num_of_frames, long block_size, ByteStream stream) throws IOException
	{
		int frame_sizes[] = new int[num_of_frames];
		
		long position = stream.position();
		
		int first_frame_size = (int)Utils.getSize(stream);
		frame_sizes[0] = first_frame_size;
		
		long total = first_frame_size;
		long prev_size = first_frame_size;
			
		for (int i = 1; i < num_of_frames-1; i++)
		{
			long s = getSizeS(stream) + prev_size;
			frame_sizes[i] = (int)s;
			total += s;
			prev_size = s;
		}
		
		block_size -= stream.position() - position;
		
		int last_frame_size = (int)(block_size - total);
		frame_sizes[num_of_frames-1] = last_frame_size;
			
		return frame_sizes;
	}
	
	private static long getSizeS(ByteStream stream) throws IOException
	{
		long size = 0;
		long s = 0x80;
		while (((size |= (stream.read() & 0xff)) & (s)) == 0)
		{
			s <<= 7;
			size <<= 8;
		}
		size ^= s;
		s--;
		s /= 2;
		size -= s;
		return size;
	}
	
	private int size_octets(long size)
	{
		if (size < 127)
			return 1;
		else if (size < 16383)
		{
			return 2;
		} else if (size < 2097151)
		{
			return 3;
		} else if (size < 268435455)
		{
			return 4;
		} else
		{
			return -1;
		}
	}
	
	private static long getSize(ByteStream stream) throws IOException
	{
		long size = 0;
		long s = 0x80;
		while (((size |= (stream.read() & 0xff)) & (s)) == 0)
		{
			s <<= 7;
			size <<= 8;
		}
		size ^= s;
		return size;
	}
}