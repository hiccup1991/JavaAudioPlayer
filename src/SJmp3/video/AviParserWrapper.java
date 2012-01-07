package SJmp3.video;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import net.sourceforge.parser.avi.avi;
import net.sourceforge.parser.avi.type.AviIndexIDX1;
import net.sourceforge.parser.avi.type.AviStreamFormat;
import net.sourceforge.parser.avi.type.AviStreamHeader;
import net.sourceforge.parser.avi.type.MainAVIHeader;
import net.sourceforge.parser.util.TreeNode;
import net.sourceforge.parser.util.TreeNodeImpl;

public class AviParserWrapper extends ParserWrapper
{
	private avi avi_parser;
	
	private TreeNode root;
	/**
	 * @param file
	 * @throws IOException 
	 */
	public AviParserWrapper(File file) throws IOException
	{
		super(file);
		
		avi_parser = new avi();
		root = avi_parser.parse(file);
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.player.ParserWrapper#dispose()
	 */
	@Override
	public void dispose()
	{
		try
		{
			avi_parser.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public int getTrackDurationSeconds(TreeNode trak)
	{
		return (int)(getTrackDuration(trak)/(getSamplerate(trak)/getTrackTimescale(trak)));
	}
	
	public int getVideoTrackDuration()
	{
		return getTrackDurationSeconds(getVideoTrack(0));
	}
	
	private double getVideoTrackTimescale()
	{
		AviStreamHeader strh = (AviStreamHeader)findChildByName("strh", getVideoTrack(0));
		return strh.dwScale;
	}
	
	private long getTrackDuration(TreeNode trak)
	{
		AviStreamHeader strh = (AviStreamHeader)findChildByName("strh", trak);
		return strh.dwLength;
	}
	
	private double getTrackTimescale(TreeNode trak)
	{
		AviStreamHeader strh = (AviStreamHeader)findChildByName("strh", trak);
		return strh.dwScale;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.player.ParserWrapper#getTracks()
	 */
	@Override
	public ArrayList<TreeNode> getTracks()
	{
		ArrayList<TreeNode> tracks = new ArrayList<TreeNode>();
		TreeNode riff_avi = findChildByName("RIFF-AVI ", root);
		TreeNode list_hdrl = findChildByName("LIST-hdrl", riff_avi);
		for (int i = 0; i < list_hdrl.getChildCount(); i++)
		{
			TreeNode child = list_hdrl.getChildAt(i);
			if ("LIST-strl".equals(child.toString()))
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
			TreeNode trak = it.next();
			if ("vids".equals(getHandlerType(trak)) && n-- == 0)
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
			TreeNode trak = it.next();
			if ("auds".equals(getHandlerType(trak)) && n-- == 0)
				return trak;
		}
		return null;
	}
	
	private String getHandlerType(TreeNode trak)
	{
		AviStreamHeader strh = (AviStreamHeader)findChildByName("strh", trak);
		return strh.fccType;
	}
	
	private String getHandler(TreeNode trak)
	{
		AviStreamHeader strh = (AviStreamHeader)findChildByName("strh", trak);
		return strh.fccHandler;
	}
	
	private AviSampleReader videoSampleReader;

	/* (non-Javadoc)
	 * @see net.sourceforge.player.ParserWrapper#startVisualSampleReader(java.util.concurrent.BlockingQueue)
	 */
	@Override
	public void startVisualSampleReader(BlockingQueue<byte[]> videoSampleQueue) throws IOException
	{
		videoSampleReader = new AviSampleReader(this, videoSampleQueue);
		
	}
	
	/* (non-Javadoc)
	 * @see net.sourceforge.player.ParserWrapper#startVisualSampleReader(java.util.concurrent.BlockingQueue, java.io.OutputStream)
	 */
	@Override
	public void startVisualSampleReader(BlockingQueue<byte[]> videoSampleQueue, InputStream audio_stream)
			throws IOException
	{
		videoSampleReader = new AviSampleReader(this, videoSampleQueue, audio_stream);
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

	/* (non-Javadoc)
	 * @see net.sourceforge.player.ParserWrapper#getVideoCodec()
	 */
	@Override
	public String getVideoCodec()
	{
		AviStreamHeader strh = (AviStreamHeader)findChildByName("strh", getVideoTrack(0));
		return strh.fccHandler;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.player.ParserWrapper#getAudioCodec()
	 */
	@Override
	public String getAudioCodec()
	{
		TreeNode audio_trak = getAudioTrack(0);
		AviStreamFormat strf = (AviStreamFormat)findChildByName("strf", audio_trak);
		
		int tag = 0;
		tag |= strf.format[1] << 8;
		tag |= strf.format[0];
		
		if (tag == 85)
			return "mp3";
		else if (tag == 8192)
			return "ac3";
		else if (tag == 8193)
			return "dts";
		
		// mp3 0x0055 - 85
		// ac3 0x2000 - 8192
		// dts 0x2001
		AviStreamHeader strh = (AviStreamHeader)findChildByName("strh", audio_trak);
		return strh.fccHandler;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.player.ParserWrapper#getVideoResolutionWidth()
	 */
	@Override
	public double getVideoResolutionWidth()
	{
		return getTrackResolutionWidth(getVideoTrack(0));
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.player.ParserWrapper#getVideoResolutionHeight()
	 */
	@Override
	public double getVideoResolutionHeight()
	{
		return getTrackResolutionHeight(getVideoTrack(0));
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.player.ParserWrapper#getTrackResolutionWidth(javax.swing.tree.TreeNode)
	 */
	@Override
	public double getTrackResolutionWidth(TreeNode trak)
	{
		AviStreamHeader strh = (AviStreamHeader)findChildByName("strh", trak);
		if (strh.width == 0)
		{
			MainAVIHeader avih = (MainAVIHeader)findChildByName("avih", trak.getParent());
			return avih.dwWidth;
		}
		return strh.width;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.player.ParserWrapper#getTrackResolutionHeight(javax.swing.tree.TreeNode)
	 */
	@Override
	public double getTrackResolutionHeight(TreeNode trak)
	{
		AviStreamHeader strh = (AviStreamHeader)findChildByName("strh", trak);
		if (strh.height == 0)
		{
			MainAVIHeader avih = (MainAVIHeader)findChildByName("avih", trak.getParent());
			return avih.dwHeight;
		}
		return strh.height;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.player.ParserWrapper#getChannelCount(javax.swing.tree.TreeNode)
	 */
	@Override
	public int getChannelCount(TreeNode trak)
	{
		return 2;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.player.ParserWrapper#getSamplerate(javax.swing.tree.TreeNode)
	 */
	@Override
	public double getVideoSamplerate()
	{
		TreeNode video_trak = getVideoTrack(0);
		AviStreamHeader strh = (AviStreamHeader)findChildByName("strh", getVideoTrack(0));
		return strh.dwRate/getTrackTimescale(video_trak);
	}
	
	@Override
	public double getAudioSamplerate()
	{
		AviStreamHeader strh = (AviStreamHeader)findChildByName("strh", getAudioTrack(0));
		return strh.dwRate;
	}
	
	private double getSamplerate(TreeNode trak)
	{
		AviStreamHeader strh = (AviStreamHeader)findChildByName("strh", trak);
		return strh.dwRate;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.player.ParserWrapper#getTrackType(javax.swing.tree.TreeNode)
	 */
	@Override
	public int getTrackType(TreeNode trak)
	{
		String handler_type = getHandlerType(trak);
		if ("vids".equals(handler_type))
		{
			return 0;
		} else if ("auds".equals(handler_type))
		{
			return 1;
		} else if ("txts".equals(handler_type))
		{
			return 2;
		}
		return -1;
	}
	
	/* (non-Javadoc)
	 * @see net.sourceforge.player.ParserWrapper#moveToPosition(int)
	 */
	@Override
	public void moveToPosition(int timeInSeconds)
	{
		AviIndexIDX1 avi_index = (AviIndexIDX1)findNode("idx1");
		if (avi_index == null) return;
		
		int duration = getTrackDurationSeconds(getVideoTrack(0));
		double s = timeInSeconds/(double)duration;
		int index = (int)(s*avi_index.idx1.capacity());
		index = Math.min(index, avi_index.idx1.capacity()-1);
		index = index - index % 4;
		
		videoSampleReader.setIndex(index);
	}
	
	public TreeNode findChildByName(String name, TreeNode node)
	{
		for (int i = 0; i < node.getChildCount(); i++)
		{
			TreeNode child = node.getChildAt(i);
			if (name.equals(child.toString()))
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
	
	public avi getParser()
	{
		return this.avi_parser;
	}
	
}