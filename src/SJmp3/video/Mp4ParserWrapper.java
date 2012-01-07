package SJmp3.video;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import net.sourceforge.parser.mp4.mp4parser;
import net.sourceforge.parser.mp4.box.AudioSampleEntry;
import net.sourceforge.parser.mp4.box.Box;
import net.sourceforge.parser.mp4.box.ChunkOffsetBox;
import net.sourceforge.parser.mp4.box.HandlerBox;
import net.sourceforge.parser.mp4.box.MediaHeaderBox;
import net.sourceforge.parser.mp4.box.SampleDescriptionBox;
import net.sourceforge.parser.mp4.box.SampleEntry;
import net.sourceforge.parser.mp4.box.SampleSizeBox;
import net.sourceforge.parser.mp4.box.SampleToChunkBox;
import net.sourceforge.parser.mp4.box.SyncSampleBox;
import net.sourceforge.parser.mp4.box.SampleToChunkBox.SampleToChunkTableEntry;
import net.sourceforge.parser.mp4.box.TrackHeaderBox;
import net.sourceforge.parser.mp4.box.VisualSampleEntry;
import net.sourceforge.parser.util.TreeNode;

public class Mp4ParserWrapper extends ParserWrapper
{
	private mp4parser mp4parser;
	private Box box;
	
	public Mp4ParserWrapper(File file) throws Exception
	{
		super(file);
		
		mp4parser = new mp4parser();
		box = (Box)mp4parser.parse(file);
	}
	
	public mp4parser getParser()
	{
		return mp4parser;
	}
	
	public void dispose()
	{
		try
		{
			mp4parser.close();
			box = null;
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	// return all tracks
	public ArrayList<TreeNode> getTracks()
	{
		ArrayList<TreeNode> tracks = new ArrayList<TreeNode>();
		TreeNode moov = findChildByName("moov", box);
		for (int i = 0; i < moov.getChildCount(); i++)
		{
			TreeNode child = moov.getChildAt(i);
			if ("trak".equals(child.toString()))
				tracks.add(child);
		}
		return tracks;
	}
	
	public TreeNode getVideoTrack(int n)
	{
		ArrayList<TreeNode> tracks = getTracks();
		Iterator<TreeNode> it = tracks.iterator();
		while (it.hasNext())
		{
			TreeNode trak = (TreeNode) it.next();
			TreeNode mdia = findChildByName("mdia", trak);
			HandlerBox hdlr = (HandlerBox)findChildByName("hdlr", mdia);
			if (hdlr != null && "vide".equals(hdlr.handler_type) && n-- == 0)
				return trak;
		}
		return null;
	}
	
	public TreeNode getAudioTrack(int n)
	{
		ArrayList<TreeNode> tracks = getTracks();
		Iterator<TreeNode> it = tracks.iterator();
		while (it.hasNext())
		{
			TreeNode trak = (TreeNode) it.next();
			TreeNode mdia = findChildByName("mdia", trak);
			HandlerBox hdlr = (HandlerBox)findChildByName("hdlr", mdia);
			if (hdlr != null && "soun".equals(hdlr.handler_type) && n-- == 0)
				return trak;
		}
		return null;
	}
	
	public SampleToChunkTableEntry[] getSampleToChunkTable(TreeNode trak)
	{
		SampleToChunkBox stsc = (SampleToChunkBox)getTrackTable(trak, "stsc");
		return stsc.table;
	}
	
	public IntBuffer getChunkOffsetTable(TreeNode trak)
	{
		ChunkOffsetBox stco = (ChunkOffsetBox)getTrackTable(trak, "stco");
		return stco.chunk_offset;
	}
	
	public IntBuffer getSampleSizeTable(TreeNode trak)
	{
		SampleSizeBox stsz = (SampleSizeBox)getTrackTable(trak, "stsz");
		return stsz.table;
	}
	
	// return one of the tables under "stbl"
	public TreeNode getTrackTable(TreeNode trak, String tableName)
	{
		TreeNode mdia = findChildByName("mdia", trak);
		if (mdia != null)
		{
			TreeNode minf = findChildByName("minf", mdia);
			if (minf != null)
			{
				TreeNode stbl = findChildByName("stbl", minf);
				if (stbl != null)
					return findChildByName(tableName, stbl);
			}
		}
		return null;
	}
	
	private String getHandlerType(TreeNode trak)
	{
		TreeNode mdia = findChildByName("mdia", trak);
		HandlerBox hdlr = (HandlerBox)findChildByName("hdlr", mdia);
		return hdlr.handler_type;
	}
	
	public int getTrackType(TreeNode trak)
	{
		String handler_type = getHandlerType(trak);
		if ("vide".equals(handler_type))
		{
			return 0;
		} else if ("soun".equals(handler_type))
		{
			return 1;
		} else if ("hint".equals(handler_type))
		{
			return 2;
		} else if ("meta".equals(handler_type))
		{
			return 3;
		}
		return -1;
	}
	
	public SampleEntry getVisualSampleEntry(int index)
	{
		TreeNode trak = getVideoTrack(0);
		SampleDescriptionBox stsd = (SampleDescriptionBox)getTrackTable(trak, "stsd");
		return stsd.sampleEntry[index];
	}
	
	public SampleEntry getAudioSampleEntry(int index)
	{
		TreeNode trak = getAudioTrack(0);
		SampleDescriptionBox stsd = (SampleDescriptionBox)getTrackTable(trak, "stsd");
		return stsd.sampleEntry[index];
	}
	
	public double getVideoResolutionWidth()
	{
		VisualSampleEntry vse = (VisualSampleEntry)getVisualSampleEntry(0);
		return vse.width;
	}
	
	public double getVideoResolutionHeight()
	{
		VisualSampleEntry vse = (VisualSampleEntry)getVisualSampleEntry(0);
		return vse.height;
	}
	
	public int getTrackDurationSeconds(TreeNode trak)
	{
		return (int)getTrackDuration(trak)/getTrackTimescale(trak);
	}
	
	public int getVideoTrackDuration()
	{
		return getTrackDurationSeconds(getVideoTrack(0));
	}
	
	public int getVideoTrackTimescale()
	{
		TreeNode mdia = findChildByName("mdia", getVideoTrack(0));
		MediaHeaderBox mdhd = (MediaHeaderBox)findChildByName("mdhd", mdia);
		return mdhd.timescale;
	}
	
	private long getTrackDuration(TreeNode trak)
	{
		TreeNode mdia = findChildByName("mdia", trak);
		MediaHeaderBox mdhd = (MediaHeaderBox)findChildByName("mdhd", mdia);
		return mdhd.duration;
	}
	
	private int getTrackTimescale(TreeNode trak)
	{
		TreeNode mdia = findChildByName("mdia", trak);
		MediaHeaderBox mdhd = (MediaHeaderBox)findChildByName("mdhd", mdia);
		return mdhd.timescale;
	}
	
	public double getTrackResolutionWidth(TreeNode trak)
	{
		TrackHeaderBox tkhd = (TrackHeaderBox)findChildByName("tkhd", trak);
		return tkhd.width;
	}
	
	public double getTrackResolutionHeight(TreeNode trak)
	{
		TrackHeaderBox tkhd = (TrackHeaderBox)findChildByName("tkhd", trak);
		return tkhd.height;
	}
	
	public int getChannelCount(TreeNode trak)
	{
		AudioSampleEntry ase = (AudioSampleEntry)getSampleEntry(trak, 0);
		return ase.channelcount;
	}
	
	public double getAudioSamplerate()
	{
		AudioSampleEntry ase = (AudioSampleEntry)getSampleEntry(sampleReader.getAudioTrack(), 0);
		return ase.samplerate;
	}
	
	public double getVideoSamplerate()
	{
		return 0;
	}
	
	public SampleEntry getSampleEntry(TreeNode trak, int index)
	{
		SampleDescriptionBox stsd = (SampleDescriptionBox)getTrackTable(trak, "stsd");
		return stsd.sampleEntry[index];
	}
	
	public String getVideoCodec()
	{
		return getSampleEntry(getVideoTrack(0), 0).toString();
	}
	
	public String getAudioCodec()
	{
		AudioSampleEntry ase = (AudioSampleEntry)getSampleEntry(getAudioTrack(0), 0);
		int c = 0;
		for (int i = 0; i < ase.decoder_config.length; i++)
		{
			if ((ase.decoder_config[i] & 0xff) > 127) continue;
			c <<= 8;
			c |= (ase.decoder_config[i] & 0xff);
			if (c >> 16 == 4)
			{
				if ((c & 0xff) == 0x40)
				{
					return "aac";
				} if ((c & 0xff) == 0x66)
				{
					return "aac - Main Profile";
				} else if ((c & 0xff) == 0x67)
				{
					return "aac - LowComplexity";
				} else if ((c & 0xff) == 0x68)
				{
					return "aac - ssr";
				} else if ((c & 0xff) == 0x6b)
				{
					return "mp3";
				}
			}
		}
		return getSampleEntry(getAudioTrack(0), 0).toString();
	}
	
	private TreeNode findChildByName(String name, TreeNode node)
	{
		for (int i = 0; i < node.getChildCount(); i++)
		{
			TreeNode child = node.getChildAt(i);
			if (name.equals(child.toString()))
				return child;
		}
		return null;
	}

	private Mp4SampleReader sampleReader;

	/* (non-Javadoc)
	 * @see net.sourceforge.player.ParserWrapper#stopVisualSampleReader()
	 */
	@Override
	public void stopVisualSampleReader() throws InterruptedException
	{
		sampleReader.release();
		sampleReader.join();
		this.dispose();
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.player.ParserWrapper#startVisualSampleReader(java.util.concurrent.BlockingQueue)
	 */
	@Override
	public void startVisualSampleReader(BlockingQueue<byte[]> videoSampleQueue) throws IOException
	{
		sampleReader = new Mp4SampleReader(this, videoSampleQueue);
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.player.ParserWrapper#startVisualSampleReader(java.util.concurrent.BlockingQueue, java.io.OutputStream)
	 */
	@Override
	public void startVisualSampleReader(BlockingQueue<byte[]> videoSampleQueue, InputStream audio)
			throws IOException
	{
		sampleReader = new Mp4SampleReader(this, videoSampleQueue, audio);
	}
	
	/* (non-Javadoc)
	 * @see net.sourceforge.player.ParserWrapper#moveToPosition(int)
	 */
	@Override
	public void moveToPosition(int timeInSeconds)
	{
		SyncSampleBox syncSampleBox = (SyncSampleBox)getTrackTable(sampleReader.getVideoTrack(), "stss");
		
		if (syncSampleBox == null) return;
		
		TreeNode video_trak = sampleReader.getVideoTrack();
		TreeNode audio_trak = sampleReader.getAudioTrack();
		
		int duration = getTrackDurationSeconds(video_trak);
		double s = timeInSeconds/(double)duration;
		int sync_index = (int)(s*syncSampleBox.table.length);
		
		sync_index = Math.min(sync_index, syncSampleBox.table.length-1);
		int sample_index = syncSampleBox.table[sync_index];
		
		SyncPoint video_sync_point = findSyncPoint_BySampleIndex(sample_index, video_trak);
		
		IntBuffer video_chunk_offset = getChunkOffsetTable(video_trak);
		IntBuffer audio_chunk_offset = getChunkOffsetTable(audio_trak);
		
		int offset = video_chunk_offset.get(video_sync_point.chunk_index);
		int audio_chunk_index = ~Mp4ParserWrapper.binarySearch(audio_chunk_offset, offset);
		SyncPoint audio_sync_point = findSyncPoint_ByChunkIndex(audio_chunk_index, audio_trak);
		
		sampleReader.setSyncPoint(video_sync_point, audio_sync_point);
	}
	
	public SyncPoint findSyncPoint_BySampleIndex(int sample_index, TreeNode trak)
	{
		SyncPoint sync_point = new SyncPoint();
		
		int samples_range = 0;
		SampleToChunkBox stsc = (SampleToChunkBox)getTrackTable(trak, "stsc");
		SampleToChunkTableEntry prev_entry = stsc.new SampleToChunkTableEntry();
		SampleToChunkTableEntry video_sample_to_chunk[] = getSampleToChunkTable(trak);
		
		for (int i = 0; i < video_sample_to_chunk.length; i++)
		{
			int num_of_samples = (video_sample_to_chunk[i].first_chunk - prev_entry.first_chunk)*prev_entry.samples_per_chunk;
			
			if (sample_index < samples_range + num_of_samples)
			{
				sync_point.chunk_index = prev_entry.first_chunk + (sample_index - samples_range)/prev_entry.samples_per_chunk -1;
				sync_point.sample_index = sample_index;
				sync_point.sample_to_chunk_index = i-1;
				sync_point.sample_index_in_chunk = (sample_index - samples_range) % prev_entry.samples_per_chunk;
				break;
			}
			samples_range += num_of_samples;
			prev_entry = video_sample_to_chunk[i];
		}
		
		if (sync_point.chunk_index == -1 && sample_index >= samples_range)
		{
			sync_point.chunk_index = prev_entry.first_chunk + (sample_index - samples_range)/prev_entry.samples_per_chunk -1;
			sync_point.sample_index = sample_index;
			sync_point.sample_to_chunk_index = video_sample_to_chunk.length-1;
			sync_point.sample_index_in_chunk = (sample_index - samples_range) % prev_entry.samples_per_chunk;
		}
		
		return sync_point;
	}
	
	public SyncPoint findSyncPoint_ByChunkIndex(int chunk_index, TreeNode trak)
	{
		SampleToChunkBox stsc = (SampleToChunkBox)getTrackTable(trak, "stsc");
		SampleToChunkTableEntry prev_entry = stsc.new SampleToChunkTableEntry();
		SampleToChunkTableEntry sample_to_chunk[] = getSampleToChunkTable(trak);
		
		IntBuffer sample_size = getSampleSizeTable(trak);
		
		SyncPoint sync_point = new SyncPoint();
		sync_point.sample_to_chunk_index = sample_to_chunk.length-1;
		sync_point.chunk_index = chunk_index;
		sync_point.first_sample_index = sample_size.get(sample_size.capacity()-1) 
													- sample_to_chunk[sample_to_chunk.length-1].samples_per_chunk;
		
		int first_sample_index = 0;
		for (int i = 0; i < sample_to_chunk.length; i++)
		{
			int num_of_samples = (sample_to_chunk[i].first_chunk - prev_entry.first_chunk)*prev_entry.samples_per_chunk;
			
			first_sample_index += num_of_samples;
			
			int sample_index = (chunk_index+1 - prev_entry.first_chunk)*prev_entry.samples_per_chunk + (first_sample_index - num_of_samples);
			
			if (chunk_index+1 < sample_to_chunk[i].first_chunk)
			{
				sync_point.chunk_index = chunk_index;
				sync_point.first_sample_index = sample_index;
				sync_point.sample_to_chunk_index = i-1;
				break;
			}
			prev_entry = sample_to_chunk[i];
		}
		
		return sync_point;
	}
	
	public class SyncPoint
	{
		public int chunk_index = -1;
		public int sample_index = -1;
		public int sample_index_in_chunk = -1;
		public int sample_to_chunk_index = -1;
		public int first_sample_index = -1;
		
		/*public void print()
		{
			System.out.println("video_chunk_index: " + video_chunk_index + ", video_sample_index:" + video_sample_index
					+ ", video_sample_index_in_chunk: " + video_sample_index_in_chunk + ", video_sample_to_chunk_index: " + video_sample_to_chunk_index
					+ ", audio_chunk_index: " + audio_chunk_index + ", audio_first_sample_index: " + audio_first_sample_index
					+ ", audio_sample_to_chunk_index: " + audio_sample_to_chunk_index );//+ ", audio_num_of_samples: " + audio_num_of_samples);
		}*/
	}
	
	public static int binarySearch(IntBuffer buffer, int key)
	{
		int low = 0;
		int high = buffer.capacity();
		int mid = 0;
		while (low < high)
		{
			mid = low + ((high - low) / 2);
			if (buffer.get(mid) < key)
	               low = mid + 1;
	           else
	                high = mid;
		}
		if ((low < buffer.capacity()) && (buffer.get(low) == key))
			return low;
		else
			return ~high;
	}
}