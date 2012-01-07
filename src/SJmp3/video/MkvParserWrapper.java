package SJmp3.video;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import net.sourceforge.parser.mkv.matroska;
import net.sourceforge.parser.mkv.type.Audio;
import net.sourceforge.parser.mkv.type.Master;
import net.sourceforge.parser.mkv.type.TrackEntry;
import net.sourceforge.parser.mkv.type.Types;
import net.sourceforge.parser.mkv.type.Video;
import net.sourceforge.parser.util.TreeNode;
import net.sourceforge.parser.util.TreeNodeImpl;

public class MkvParserWrapper extends ParserWrapper
{
	private matroska matroska;
	
	private TreeNode root;
	
	private MkvSampleReader videoSampleReader;
	
	public TreeNode getRoot()
	{
		return root;
	}
	
	public matroska getParser()
	{
		return this.matroska;
	}

	/**
	 * @param file
	 * @throws Exception 
	 */
	public MkvParserWrapper(File file) throws Exception
	{
		super(file);
		
		matroska = new matroska();
		root = matroska.parse(file);
	}
	
	public TreeNode getVideoTrack(int n)
	{
		ArrayList<TreeNode> tracks = getTracks();
		Iterator<TreeNode> it = tracks.iterator();
		while (it.hasNext())
		{
			TrackEntry track_entry =  (TrackEntry)it.next();
			if (track_entry.TrackType == 1)
				return track_entry;
		}
		return null;
	}
	
	public TreeNode getAudioTrack(int n)
	{
		ArrayList<TreeNode> tracks = getTracks();
		Iterator<TreeNode> it = tracks.iterator();
		while (it.hasNext())
		{
			TrackEntry track_entry =  (TrackEntry)it.next();
			if (track_entry.TrackType == 2)
				return track_entry;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.player.ParserWrapper#dispose()
	 */
	@Override
	public void dispose()
	{
		try
		{
			matroska.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.player.ParserWrapper#getAudioCodec()
	 */
	@Override
	public String getAudioCodec()
	{
		TrackEntry audio_track = (TrackEntry)getAudioTrack(0);
		return audio_track.CodecID;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.player.ParserWrapper#getAudioSamplerate()
	 */
	@Override
	public double getAudioSamplerate()
	{
		TrackEntry audio_track = (TrackEntry)getAudioTrack(0);
		Audio audio = (Audio)findMaster("Audio", audio_track);
		return audio.SamplingFrequency;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.player.ParserWrapper#getChannelCount(javax.swing.tree.TreeNode)
	 */
	@Override
	public int getChannelCount(TreeNode trak)
	{
		TrackEntry audio_track = (TrackEntry)getAudioTrack(0);
		Audio audio = (Audio)findMaster("Audio", audio_track);
		return audio.Channels;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.player.ParserWrapper#getTrackDurationSeconds(javax.swing.tree.TreeNode)
	 */
	@Override
	public int getTrackDurationSeconds(TreeNode trak)
	{
		return -1;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.player.ParserWrapper#getTrackResolutionHeight(javax.swing.tree.TreeNode)
	 */
	@Override
	public double getTrackResolutionHeight(TreeNode trak)
	{
		Video video = (Video)findMaster("Video", trak);
		return video.PixelHeight;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.player.ParserWrapper#getTrackResolutionWidth(javax.swing.tree.TreeNode)
	 */
	@Override
	public double getTrackResolutionWidth(TreeNode trak)
	{
		Video video = (Video)findMaster("Video", trak);
		return video.PixelWidth;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.player.ParserWrapper#getTracks()
	 */
	@Override
	public ArrayList<TreeNode> getTracks()
	{
		ArrayList<TreeNode> list = new ArrayList<TreeNode>();
		TreeNode segment = findMaster("Segment", root); // first seg
		TreeNode tracks = findMaster("Tracks", segment);
		for (int i = 0; i < tracks.getChildCount(); i++)
		{
			Master track = (Master)tracks.getChildAt(i);
			if ("Track Entry".equals(Types.ebml_id.get(track.ebml_id)))
				list.add(track);
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.player.ParserWrapper#getTrackType(javax.swing.tree.TreeNode)
	 */
	@Override
	public int getTrackType(TreeNode trak)
	{
		TrackEntry track_entry =  (TrackEntry)trak;
		return track_entry.TrackType-1;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.player.ParserWrapper#getVideoCodec()
	 */
	@Override
	public String getVideoCodec()
	{
		TrackEntry video_track = (TrackEntry)getVideoTrack(0);
		if (new String(video_track.CodecPrivate).indexOf("XVID") >= 0)
			return "XviD";
		return video_track.CodecID;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.player.ParserWrapper#getVideoResolutionHeight()
	 */
	@Override
	public double getVideoResolutionHeight()
	{
		Video video = (Video)findMaster("Video", getVideoTrack(0));
		return video.PixelHeight;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.player.ParserWrapper#getVideoResolutionWidth()
	 */
	@Override
	public double getVideoResolutionWidth()
	{
		Video video = (Video)findMaster("Video", getVideoTrack(0));
		return video.PixelWidth;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.player.ParserWrapper#getVideoSamplerate()
	 */
	@Override
	public double getVideoSamplerate()
	{
		Video video = (Video)findMaster("Video", getVideoTrack(0));
		return video.FrameRate;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.player.ParserWrapper#getVideoTrackDuration()
	 */
	@Override
	public int getVideoTrackDuration()
	{
		TrackEntry track = (TrackEntry)getVideoTrack(0);
		return (int)track.DefaultDuration;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.player.ParserWrapper#moveToPosition(int)
	 */
	@Override
	public void moveToPosition(int time_in_seconds)
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.player.ParserWrapper#startVisualSampleReader(java.util.concurrent.BlockingQueue)
	 */
	@Override
	public void startVisualSampleReader(BlockingQueue<byte[]> videoSampleQueue) throws IOException
	{
		videoSampleReader = new MkvSampleReader(this, videoSampleQueue);
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.player.ParserWrapper#startVisualSampleReader(java.util.concurrent.BlockingQueue, java.io.InputStream)
	 */
	@Override
	public void startVisualSampleReader(BlockingQueue<byte[]> videoSampleQueue, InputStream audio)
			throws IOException
	{
		videoSampleReader = new MkvSampleReader(this, videoSampleQueue, audio);
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.player.ParserWrapper#stopVisualSampleReader()
	 */
	@Override
	public void stopVisualSampleReader() throws InterruptedException
	{
		videoSampleReader.release();
		videoSampleReader.join();
		this.dispose();
	}
	
	public TreeNode findMaster(String name, TreeNode node)
	{
		for (int i = 0; i < node.getChildCount(); i++)
		{
			Master child = (Master)node.getChildAt(i);
			if (name.equals(Types.ebml_id.get(child.ebml_id)))
				return child;
		}
		return null;
	}
	
	public TreeNode findNode(String name)
	{
		TreeNodeImpl next = (TreeNodeImpl)root;
		while(next != null)
		{
			next = next.getNextNode();
			if (name.equals(next.toString())) return next;
		}
		return null;
	}

}
