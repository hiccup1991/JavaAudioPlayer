package SJmp3.video;

import java.util.ArrayList;
import java.util.Iterator;
import net.sourceforge.parser.util.TreeNode;

public class MediaInfo
{
	private StringBuffer info = new StringBuffer();
	
	public MediaInfo(View view)
	{
		ParserWrapper wrapper = view.getPlayer().getParserWrapper();
		String fileName = view.getPlayer().getFile().getName();
		info.append("<h5>" + fileName + "</h5>");
		
		ArrayList<TreeNode> list = wrapper.getTracks();
		Iterator<TreeNode> it = list.iterator();
		while (it.hasNext())
		{
			TreeNode trak = it.next();
			int track_type = wrapper.getTrackType(trak);
			
			switch (track_type)
		    {
			case 0:
			{
				int duration = wrapper.getTrackDurationSeconds(trak);
				int width = (int)wrapper.getTrackResolutionWidth(trak);
				int height = (int)wrapper.getTrackResolutionHeight(trak);
				String codec = wrapper.getVideoCodec();
				
				double samplerate = wrapper.getVideoSamplerate();
				samplerate = Math.round((samplerate*1000))/1000.0;
				if (samplerate == 0)
					samplerate = view.getScreen().getFPS();
				
				info.append("<h5>Video</h5>");
				info.append("Codec: " + codec +  "<br/>");
				info.append("Rate: " + samplerate + " fps" + "<br/>");
				info.append("Resolution: " + width + "x" + height + "<br/>");
				info.append("Duration: " + (duration/60) + " min" + "<br/>");
				break;
			}
			case 1:
				long duration = wrapper.getTrackDurationSeconds(trak);
				String codec = wrapper.getAudioCodec();
				int channelcount = wrapper.getChannelCount(trak);
				double samplerate = wrapper.getAudioSamplerate();
				
				info.append("<h5>Audio</h5>");
				info.append("Codec: " + codec +  "<br/>");
				info.append("Channels: " + channelcount + "<br/>");
				info.append("Samplerate: " + samplerate + "<br/>");
				info.append("Duration: " + (duration/60) + " min" + "<br/>");
				break;
			case 2:
				break;
			case 3:
				break;
		    }
		}
	}
	
	public String getInfo()
	{
		return info.toString();
	}
}