package SJmp3.video.stream;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.BlockingQueue;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JWindow;
import SJmp3.video.Resources;
import SJmp3.video.Screen;
import org.xvid.YV12Image;

public class TSView extends JFrame
{
	private static final long serialVersionUID = -9205050998192954949L;

	private Screen screen;
	
	private JPanel mainPanel;
	
	private JSlider slider;
	
	public TSView()
	{
		super("jv_player");
		
		mainPanel = new JPanel(new BorderLayout());
				
		//mainPanel.add(BorderLayout.NORTH, createToolBar());
		
		//this.setJMenuBar(createMenuBar());
		
		Container content = getContentPane();
		content.add(mainPanel);
		
		setIconImage(Resources.java_coffe_32.getImage());
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		centralize();
		
		
	}
	
	private JPanel screenPanel;
	
	private JWindow fullScreenWindow;
	
	
	public void showScreen(BlockingQueue<YV12Image> decodedVideoSampleQueue, BlockingQueue<byte[]> subtitles, int width, int height, double samplerate)
	{
		screen = new Screen(width, height, samplerate, decodedVideoSampleQueue, subtitles);
		screen.addMouseListener(new ExitFullScreen());
		
		//int duration_in_seconds = player.getParserWrapper().getVideoTrackDuration();
		
		screenPanel = new JPanel(new BorderLayout());
		
		screenPanel.add(BorderLayout.CENTER, screen);
		mainPanel.add(BorderLayout.CENTER, screenPanel);
		mainPanel.revalidate();
		
		//btnPlay.setIcon(Resources.pause16);
		pack();
	}
	
	public JButton getPlayButton()
	{
		return btnPlay;
	}
	
	private JButton btnPlay;
	private JButton btnStop;
	
	
	public void updateOnStop()
	{
		mainPanel.remove(screenPanel);
		TSView.this.setSize(new Dimension(400, TSView.this.getSize().height - screenPanel.getSize().height));
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
	
	private void centralize()
	{
		int x = (Toolkit.getDefaultToolkit().getScreenSize().width - getWidth()) / 2;
		int y = (Toolkit.getDefaultToolkit().getScreenSize().height - getHeight()) / 2;
		setLocation(x, y);
	}
	
	class FullScreenAction extends AbstractAction
    {
    	 private static final long serialVersionUID = 6721985548997200331L;

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
				
				int state = TSView.this.getExtendedState();
				Dimension dim = TSView.this.getSize();
				TSView.this.setSize(0, 0);
				TSView.this.setSize(dim);
				TSView.this.setExtendedState(state);
			}
		}
	}

}
