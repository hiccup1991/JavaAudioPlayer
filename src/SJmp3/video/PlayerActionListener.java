package SJmp3.video;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class PlayerActionListener implements ActionListener
{
	public static final int STOP = 0;
	
	public static final int PLAY = 1;
	
	public static final int PAUSE = 2;
	
	public static final int FAST_FORWORD = 3;
	
	private View view;
	
	public PlayerActionListener(View view)
	{
		this.view = view;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		String command = null;
		JButton button = null;
		if (source instanceof JButton)
		{
			button = (JButton)e.getSource();
			command = button.getActionCommand();
		}
		
		if (command != null)
		{
			int mode = view.getPlayer().getMode();
			
			if (command.equals(Resources.PLAY))
			{
				try
				{
					if (mode == STOP)
					{
						view.getPlayer().play();
						
					} else if (mode == PAUSE)
					{
						view.getScreen().resume();
					}
					view.getPlayer().audioResume();
					button.setIcon(Resources.pause16);
					button.setActionCommand(Resources.PAUSE);
					view.getPlayer().setMode(PLAY);
					
				} catch (Exception e1)
				{
					e1.printStackTrace();
				}
			} else if (command.equals(Resources.PAUSE))
			{
				if (view.isRunning())
				{
					if (mode == PLAY)
					{
						view.getPlayer().audioPause();
						view.getScreen().pause();
						button.setIcon(Resources.play16);
						button.setActionCommand(Resources.PLAY);
						view.getPlayer().setMode(PAUSE);
					}
				}
			} else if (command.equals(Resources.STOP))
			{
				view.getPlayer().stop();
				view.getPlayButton().setIcon(Resources.play16);
				view.getPlayButton().setActionCommand(Resources.PLAY);
				view.getPlayer().setMode(STOP);				
			} else if (command.equals(Resources.FAST_FORWORD))
			{
				if (view.isRunning())
				{
					if (view.getScreen().fastForward() > 1)
					{
						view.getPlayer().skipAudio(true);
						view.getPlayer().setMode(FAST_FORWORD);
					} else
					{
						view.getPlayer().skipAudio(false);
						view.getPlayer().setMode(PLAY);
					}
				}
			}
		}
	}
}