package SJmp3.video;

import java.io.InputStream;
import javax.swing.ImageIcon;

public class Resources
{
	public static final String FILE = "File";
	public static final String OPEN_FILE = "Open...";
	public static final String OPEN_STREAM = "Open Stream...";
	public static final String CLOSE = "Close";
	public static final String VIEW = "View";
	public static final String HELP = "Help";
	public static final String ABOUT = "About";
	public static final String MEDIA_INFO = "Media Info";
	
	public static final String PLAY = "Play";
	public static final String PAUSE = "pause";
	public static final String STOP = "stop";
	public static final String FAST_FORWORD = "fast forward";
	public static final String FULL_SCREEN = "full screen";
	
	public static ImageIcon open16;
        public static ImageIcon info16;
	public static ImageIcon save16;
	public static ImageIcon url16;  
	public static ImageIcon exit16;         
	public static ImageIcon preferences16;
	public static ImageIcon play16;
	public static ImageIcon pause16;
	public static ImageIcon stop16;
	public static ImageIcon movie16;
	public static ImageIcon help16;
	public static ImageIcon about16;
	public static ImageIcon volume16;
	public static ImageIcon fastforward16;
	public static ImageIcon java_coffe_32;
	public static ImageIcon full_screen;
	
	static
	{
		open16 = loadIconImage("SJmp3/img/16x16/open-folder-plus-16.png");
                info16 = loadIconImage("SJmp3/img/16x16/info-green-16.png");
                url16 = loadIconImage("SJmp3/img/16x16/world_connect-16.png");
                exit16 = loadIconImage("SJmp3/img/16x16/quit.png");                
		save16 = loadIconImage("SJmp3/video/icon/Save16.gif");
		preferences16 = loadIconImage("SJmp3/video/icon/Preferences16.gif");
		play16 = loadIconImage("SJmp3/img/16x16/play_green-1.png");
		pause16 = loadIconImage("SJmp3/img/16x16/control_pause.png");
		stop16 = loadIconImage("SJmp3/img/16x16/stop_red.png");
		movie16 = loadIconImage("SJmp3/video/icon/Movie16.gif");
		help16 = loadIconImage("SJmp3/video/icon/Help16.gif");
		about16 = loadIconImage("SJmp3/video/icon/About16.gif");
		volume16 = loadIconImage("SJmp3/video/icon/Volume16.gif");
		fastforward16 = loadIconImage("SJmp3/video/icon/FastForward16.gif");
		java_coffe_32 = loadIconImage("SJmp3/video/icon/Java-coffe-32.png");
		full_screen = loadIconImage("SJmp3/video/icon/full_screen_16.png");
	}
	
	public static ImageIcon loadIconImage(String name)
	{
		ImageIcon icon = null;
		InputStream stream = ClassLoader.getSystemResourceAsStream(name);
		try
		{
			byte b[] = new byte[stream.available()];
			stream.read(b);
			stream.close();
			icon = new ImageIcon(b);
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return icon;
	}

}
