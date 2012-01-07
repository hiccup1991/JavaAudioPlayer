package SJmp3.video;

import SJmp3.SJmp3gui;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.BlockingQueue;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.JWindow;
import org.xvid.YV12Image;

public class View extends JFrame
{
	//private static final long serialVersionUID = -307447136771388349L;
	
	private jv_player player;
	
	private Screen screen;
	
	private JPanel mainPanel;
	
	public JSlider slider;
	
	public View(jv_player player)
	{
		super("Video Player");
		
		this.player = player;
		
		mainPanel = new JPanel(new BorderLayout());
				
		mainPanel.add(BorderLayout.NORTH, createToolBar());
		
		//this.setJMenuBar(createMenuBar());
		
		Container content = getContentPane();
		content.add(mainPanel);
		
		setIconImage(Resources.java_coffe_32.getImage());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                //setLocationRelativeTo(SJmp3gui.frame);
                setIconImage(SJmp3gui.frame.FrameIcon.getImage());
                this.setLocation(100,100);
		pack();
		//centralize();
		
		addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				getPlayer().stop();
				dispose();
			}
		});
		
		new Timer();
	}
	
	private JPanel screenPanel;
	
	private JWindow fullScreenWindow;
	
	private boolean update_slider = true;
	private int test_ts = 0;
	
	
	public void showScreen(BlockingQueue<YV12Image> decodedVideoSampleQueue, int width, int height, double samplerate)
	{
		screen = new Screen(width, height, samplerate, decodedVideoSampleQueue, null);
		screen.addMouseListener(new ExitFullScreen());
		
		int duration_in_seconds = player.getParserWrapper().getVideoTrackDuration();
		
		screenPanel = new JPanel(new BorderLayout());
		slider = new JSlider(0, duration_in_seconds);
		test_ts = 0;
		slider.addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
				update_slider = false;
			}
			
			public void mouseReleased(MouseEvent e)
			{
				player.getParserWrapper().moveToPosition(slider.getValue());
				test_ts = slider.getValue() - screen.getCurrentTime();
				update_slider = true;
			}
		});
		
		screenPanel.add(BorderLayout.CENTER, screen);
		screenPanel.add(BorderLayout.SOUTH, slider);
		mainPanel.add(BorderLayout.CENTER, screenPanel);
		mainPanel.revalidate();
		
		btnPlay.setIcon(Resources.pause16);
		pack();
	}
	
	public jv_player getPlayer()
	{
            return this.player;
	}
	
	public JButton getPlayButton()
	{
            return btnPlay;
	}
	
	private JButton btnPlay;
	private JButton btnStop;
        private JButton btnInfo;
        private JButton btnOpenFile;
        private JButton btnOpenUrl;
        private JButton btnExit;        
	private PlayerActionListener pal = new PlayerActionListener(this);
	
	private JPanel createToolBar()
	{
	btnOpenFile = new JButton(Resources.open16);
    	btnOpenFile.setActionCommand(Resources.OPEN_FILE);
    	btnOpenFile.addActionListener(fal);
		
    	btnPlay = new JButton(Resources.play16);
    	btnPlay.setActionCommand(Resources.PAUSE);
	btnPlay.addActionListener(pal);
		
	btnStop = new JButton(Resources.stop16);
	btnStop.setActionCommand(Resources.STOP);
	btnStop.addActionListener(pal);
        
        btnInfo = new JButton(Resources.info16);
	btnInfo.setActionCommand(Resources.MEDIA_INFO);
	btnInfo.addActionListener(fal);
        
        btnOpenUrl = new JButton(Resources.url16);
	btnOpenUrl.setActionCommand(Resources.OPEN_STREAM);
	btnOpenUrl.addActionListener(fal); 
        
	btnExit = new JButton(Resources.exit16);
	btnExit.setActionCommand(Resources.CLOSE);
	btnExit.addActionListener(fal);        
		
	//JButton btnFastForward = new JButton(Resources.fastforward16);
	//btnFastForward.setActionCommand(Resources.FAST_FORWORD);
	//btnFastForward.addActionListener(pal);
		
	JButton btnFullScreen = new JButton();
	btnFullScreen.setAction(new FullScreenAction(null, Resources.full_screen, Resources.FULL_SCREEN, null));
		
	JPanel pnl = new JPanel();
    	pnl.add(btnOpenFile);
    	pnl.add(btnOpenUrl);        
    	//pnl.add(new JToolBar.Separator());
    	pnl.add(btnPlay);
    	pnl.add(btnStop);
    	pnl.add(btnInfo);        
    	//pnl.add(btnFastForward);
    	pnl.add(btnFullScreen);
    	//pnl.add(new JToolBar.Separator());  
    	pnl.add(btnExit);         
    	
    	return pnl;
	}
	
	public void updateOnStop()
	{
		mainPanel.remove(screenPanel);
		View.this.setSize(new Dimension(400, View.this.getSize().height - screenPanel.getSize().height));
		btnPlay.setIcon(Resources.play16);
		mainPanel.revalidate();
	}
	
	public Screen getScreen()
	{
		return this.screen;
	}
	
	public boolean isRunning()
	{
		if (screen != null)
			return screen.isScreenThreadAlive();
		return false;
	}
	
	private FileActionListener fal = new FileActionListener(this);
	
	public JMenuBar createMenuBar()
	{
		JMenuItem miOpen = new JMenuItem(Resources.OPEN_FILE, Resources.open16);
		JMenuItem miOpenStream = new JMenuItem(Resources.OPEN_STREAM);
		JMenuItem miClose = new JMenuItem(Resources.CLOSE);
		JMenu fileManu = new JMenu(Resources.FILE);
		//fileManu.add(new JPopupMenu.Separator());
		fileManu.add(miOpen);
		fileManu.add(miOpenStream);
		fileManu.add(miClose);
		
		JMenu viewManu = new JMenu(Resources.VIEW);
		JMenuItem miMediaInfo = new JMenuItem(Resources.MEDIA_INFO);
		viewManu.add(miMediaInfo);
		
		JMenu helpManu = new JMenu(Resources.HELP);
		JMenuItem miAbout = new JMenuItem(Resources.ABOUT, Resources.about16);
		helpManu.add(miAbout);

		miOpen.addActionListener(fal);
		miOpenStream.addActionListener(fal);
		miClose.addActionListener(fal);
		miMediaInfo.addActionListener(fal);
		//miAbout.addActionListener(fal);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(fileManu);
		//menuBar.add(viewManu);
		//menuBar.add(helpManu);
		
		return menuBar;
	}
	
	private void centralize()
	{
		int x = (Toolkit.getDefaultToolkit().getScreenSize().width ) / 2;
		int y = (Toolkit.getDefaultToolkit().getScreenSize().height ) / 2;
		setLocation(x, y);
	}
	
	class FullScreenAction extends AbstractAction
        {
    	 //private static final long serialVersionUID = 6721985548997200331L;

    	 public FullScreenAction(String text, ImageIcon icon, String desc, Integer mnemonic)
         {
    		 super(text, icon);
             putValue(SHORT_DESCRIPTION, desc);
             putValue(MNEMONIC_KEY, mnemonic);
         }
    	
    	 @Override
    	 public void actionPerformed(ActionEvent e) 
    	 {
			Object source = e.getSource();
			if (source instanceof JButton)
			{
				if (isRunning())
				{
					setVisible(false);
					fullScreenWindow = new JWindow();
					fullScreenWindow.add(screen);
					GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
					GraphicsDevice gs = ge.getDefaultScreenDevice(); gs.setFullScreenWindow(fullScreenWindow);
				}
			}
    	 }	
    }
	
	class ExitFullScreen extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			if (fullScreenWindow == null) return;
			
			if (e.getClickCount() > 1)
			{
				GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
				GraphicsDevice gs = ge.getDefaultScreenDevice(); gs.setFullScreenWindow(null);
				screenPanel.add(BorderLayout.CENTER, screen);
				setVisible(true);
				fullScreenWindow.dispose();
				fullScreenWindow = null;
				
				int state = View.this.getExtendedState();
				Dimension dim = View.this.getSize();
				View.this.setSize(0, 0);
				View.this.setSize(dim);
				View.this.setExtendedState(state);
			}
		}
	}
	
	public class Timer extends Thread
	{
		private long seconds = 0;
		
		private boolean stop = false;
		
		public Timer()
		{
			setDaemon(true);
			start();
		}
		
		public void run()
		{
			try
			{
				long start_time = System.currentTimeMillis();
				long err = 0;
				while(!stop)
				{
					err = (System.currentTimeMillis() - start_time) % 1000;
					seconds++;
					Thread.sleep(1000-err);
					
					if (slider != null)
					{
						if (update_slider)
						{
							slider.setValue(screen.getCurrentTime() + test_ts);
						}
						// test
						//if (slider.getMaximum() <= screen.getCurrentTime()) player.stop();
					}
				}
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}