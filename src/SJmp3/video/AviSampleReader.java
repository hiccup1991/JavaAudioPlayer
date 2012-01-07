package SJmp3.video;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import net.sourceforge.parser.avi.Utils;
import net.sourceforge.parser.avi.type.AviIndexChunk;
import net.sourceforge.parser.avi.type.AviIndexIDX1;
import net.sourceforge.parser.avi.type.AviStandardIndex;
import net.sourceforge.parser.avi.type.AviStreamFormat;
import net.sourceforge.parser.avi.type.List;
import net.sourceforge.parser.util.ByteStream;
import net.sourceforge.parser.util.TreeNode;

public class AviSampleReader extends Thread
{
	private boolean release_flag = false;
	private BlockingQueue<byte[]> videoSampleQueue;
	private AviParserWrapper wrapper;
	
	private PipedInputStream audio_stream;
	private PipedOutputStream audio_out;
	
	public AviSampleReader(AviParserWrapper wrapper, BlockingQueue<byte[]> videoSampleQueue) throws IOException
	{
		this(wrapper, videoSampleQueue, null);
	}
	
	public AviSampleReader(AviParserWrapper wrapper, BlockingQueue<byte[]> videoSampleQueue, InputStream audio_stream) throws IOException
	{
		this.wrapper = wrapper;
		this.videoSampleQueue = videoSampleQueue;
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
	
	public void readSamples() throws Exception
	{
		ByteStream stream = wrapper.getParser().getByteStream();

		AviStreamFormat strf = (AviStreamFormat)wrapper.findChildByName("strf", wrapper.getVideoTrack(0));
		
		AviIndexChunk video_super_index = (AviIndexChunk)wrapper.findChildByName("indx", wrapper.getVideoTrack(0));
		AviIndexChunk audio_super_index = (AviIndexChunk)wrapper.findChildByName("indx", wrapper.getAudioTrack(0));
		
		if (video_super_index == null || audio_super_index == null)
		{
			readSamplesIDX1();
			return;
		}
		
		nextVideoStdIndex(video_super_index, stream);
		nextAudioStdIndex(audio_super_index, stream);
		
		boolean video_end = false;
		boolean audio_end = false;
		
		while(!release_flag || (video_end && audio_end))
		{
			long video_offset = nextVideoOffset(video_super_index, stream);
			long audio_offset = nextAudioOffset(audio_super_index, stream);
			
			video_end = video_offset == -1;
			audio_end = audio_offset == -1;
			
			video_offset += video_std_index.qwBaseOffset;
			audio_offset += audio_std_index.qwBaseOffset;
			
			if (video_offset < audio_offset)
			{
				if (video_end) continue;
				
				video_index++;
				int sample_size = video_std_index.idx.get(video_index++);

				boolean key_frame = sample_size > 0;
				if (!key_frame)
					sample_size ^= (1 << 31);
				
				byte[] sample_data = new byte[sample_size];
				stream.position(video_offset);
				stream.read(sample_data);
				
				//VideoSample sample = new VideoSample(sample_data, strf.format, 0);

				if(!release_flag)
					videoSampleQueue.offer(sample_data, 1 << 25, TimeUnit.MILLISECONDS);
				
			} else
			{
				if (audio_end) continue;
				
				audio_index++;
				int sample_size = audio_std_index.idx.get(audio_index++);

				boolean key_frame = sample_size > 0;
				if (!key_frame)
					sample_size ^= (1 << 31);

				if (audio_stream != null)
				{
					byte[] sample_data = new byte[sample_size];
					stream.position(audio_offset);
					stream.read(sample_data);
					audio_out.write(sample_data);
				}
			}
		}
	}
	
	private int video_index;
	private int audio_index;
	
	private AviStandardIndex video_std_index;
	private AviStandardIndex audio_std_index;
	
	private long nextVideoOffset(AviIndexChunk video_super_index, ByteStream stream) throws IOException
	{
		if (video_index >= video_std_index.idx.capacity())
		{
			nextVideoStdIndex(video_super_index, stream);
			if (video_std_index == null) return -1;
			video_index = 0;
		}
		return video_std_index.idx.get(video_index);
	}
	
	private void nextVideoStdIndex(AviIndexChunk video_super_index, ByteStream stream) throws IOException
	{
		long dwOffset = video_super_index.idx.get();
		
		if (dwOffset == 0)
		{
			video_std_index = null;
		} else
		{
			long dwSize_dwDuration = video_super_index.idx.get();
			
			stream.position(dwOffset);
			int type = (int)Utils.bytesToLong(stream, 4);
			long size = (int) Utils.bytesToLongLE(stream, 4);
			video_std_index = new AviStandardIndex(type, size);
			video_std_index.readData(stream);
		}
	}
	
	private long nextAudioOffset(AviIndexChunk audio_super_index, ByteStream stream) throws IOException
	{
		if (audio_index >= audio_std_index.idx.capacity())
		{
			nextAudioStdIndex(audio_super_index, stream);
			if (audio_std_index == null) return -1;
			audio_index = 0;
		}
		return audio_std_index.idx.get(audio_index);
	}
	
	private void nextAudioStdIndex(AviIndexChunk audio_super_index, ByteStream stream) throws IOException
	{
		long dwOffset = audio_super_index.idx.get();
		
		if (dwOffset == 0)
		{
			audio_std_index = null;
		} else
		{
			long dwSize_dwDuration = audio_super_index.idx.get();
			
			stream.position(dwOffset);
			int type = (int)Utils.bytesToLong(stream, 4);
			long size = (int) Utils.bytesToLongLE(stream, 4);
			audio_std_index = new AviStandardIndex(type, size);
			audio_std_index.readData(stream);
		}
	}
	
	private boolean new_index = false;
	private int index = 0;
	
	public synchronized void setIndex(int index)
	{
		this.index = index;
		this.new_index = true;
		videoSampleQueue.clear();
	}
	
	private static final int xt = 0x7874;
	private static final int bw = 0x6277;
	private static final int cd = 0x6364;
	private static final int bd = 0x6264;
	private static final int xi = 0x7869;
	
	public void readSamplesIDX1() throws Exception
	{
		List movie = (List)wrapper.findNode("LIST-movi");

		AviStreamFormat strf = (AviStreamFormat)wrapper.findChildByName("strf", wrapper.getVideoTrack(0));
		
		TreeNode node = wrapper.findNode("idx1");
		AviIndexIDX1 avi_index = (AviIndexIDX1)node;
		
		boolean rel_pos = true;
		
		while(index < avi_index.idx1.capacity())
		{
			if (release_flag) return;
			
			int ckid = avi_index.idx1.get(index++);
			int dwFlags = avi_index.idx1.get(index++);
			int dwChunkOffset = avi_index.idx1.get(index++);
			int dwChunkLength = avi_index.idx1.get(index++);
			
			if (index == 4 && (dwChunkOffset == movie.offset)) rel_pos = false;
			
			if (rel_pos)
			{
				wrapper.getParser().getByteStream().position((int)movie.offset + dwChunkOffset+4);
			} else
				wrapper.getParser().getByteStream().position(dwChunkOffset+8);
			
			if ((ckid >>> 16) == cd)
			{
				byte[] sample_data = new byte[dwChunkLength];
				wrapper.getParser().getByteStream().read(sample_data);
				//VideoSample sample = new VideoSample(sample_data, strf.format, 0);
				videoSampleQueue.offer(sample_data, 1<<25, TimeUnit.MILLISECONDS);
			} else if ((ckid >>> 16) == bd)
			{
				byte[] sample_data = new byte[dwChunkLength];
				wrapper.getParser().getByteStream().read(sample_data);
				//VideoSample sample = new VideoSample(sample_data, strf.format, 0);
				videoSampleQueue.offer(sample_data, 1<<25, TimeUnit.MILLISECONDS);
			} else if ((ckid >>> 16) == bw)
			{
				if (audio_stream != null)
				{
					byte[] sample_data = new byte[dwChunkLength];
					wrapper.getParser().getByteStream().read(sample_data);
					audio_out.write(sample_data);
				}
			} else if ((ckid >>> 16) == xt)
			{}
		}
	}
}