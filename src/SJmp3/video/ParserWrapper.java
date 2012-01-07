package SJmp3.video;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import net.sourceforge.parser.util.TreeNode;

public abstract class ParserWrapper
{
	public static ParserWrapper newParserWrapper(File file) throws Exception
	{
		if (file.getName().endsWith(".mp4"))
		{
			return new Mp4ParserWrapper(file);
		} else if (file.getName().endsWith(".avi"))
		{
			return new AviParserWrapper(file);
		} else if (file.getName().endsWith(".mkv"))
		{
			return new MkvParserWrapper(file);
		}
		
		return null;
	}
	
	public ParserWrapper(File file)
	{
		
	}
	
	public abstract void dispose();
	
	public abstract String getAudioCodec();
	
	public abstract double getAudioSamplerate();
	
	public abstract int getChannelCount(TreeNode trak);
		
	public abstract int getTrackDurationSeconds(TreeNode trak);
	
	public abstract double getTrackResolutionHeight(TreeNode trak);
	
	public abstract double getTrackResolutionWidth(TreeNode trak);
	
	public abstract ArrayList<TreeNode> getTracks();
	
	public abstract int getTrackType(TreeNode trak);
	
	public abstract String getVideoCodec();
	
	public abstract double getVideoResolutionHeight();
	
	public abstract double getVideoResolutionWidth();
	
	public abstract double getVideoSamplerate();
	
	public abstract int getVideoTrackDuration();
	
	public abstract void moveToPosition(int time_in_seconds);
	
	public abstract void startVisualSampleReader(BlockingQueue<byte[]> videoSampleQueue)  throws IOException;
	
	public abstract void startVisualSampleReader(BlockingQueue<byte[]> videoSampleQueue, InputStream audio)  throws IOException;
	
	public abstract void stopVisualSampleReader()  throws InterruptedException;
}
