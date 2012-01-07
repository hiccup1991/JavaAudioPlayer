package SJmp3.video;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import SJmp3.video.stream.ts_player;

public class FileActionListener implements ActionListener
{
	private File current_dir = null;
	
	private View view;
	
	FileActionListener(View view)
	{
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		String command = null;
		if (source instanceof JMenuItem)
		{
			JMenuItem item = (JMenuItem)e.getSource();
			command = item.getActionCommand();
		} else if (source instanceof JButton)
		{
			JButton item = (JButton)e.getSource();
			command = item.getActionCommand();
		}
		
		if (command != null)
		{			
			if (command.equals(Resources.OPEN_FILE))
			{
				JFileChooser chooser = new JFileChooser (current_dir);
		    	chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		    	chooser.addChoosableFileFilter(new MediaFileFilter());
		    	int ret = chooser.showOpenDialog(view);
		    	if (ret == JFileChooser.APPROVE_OPTION)
		    	{
		    		current_dir = chooser.getCurrentDirectory();
		    		final File file = chooser.getSelectedFile();
		    		view.getPlayer().setFile(file);
		    		try
					{
                                            //view.slider.setValue(0);
                                            view.getPlayer().play();
					} catch (Exception e1)
					{
						e1.printStackTrace();
					}
		    	}
			} else if (command.equals(Resources.OPEN_STREAM))
			{
				try
				{
					final String res = JOptionPane.showInputDialog(view, "Enter url to connect:\n", "http://localhost:1234");
					if (res != null)
					{
						new Thread()
						{
							public void run()
							{
								try
								{
									new ts_player(res);
								} catch (Exception e)
								{
									e.printStackTrace();
								}
							}
						}.start();
					}
				} catch (Exception e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (command.equals(Resources.CLOSE))
			{
				if (view.getPlayer().isRunning())
					view.getPlayer().stop();
				view.dispose();
			} else if (command.equals(Resources.MEDIA_INFO))
			{
				if (view.getPlayer().isRunning())
				{
					new InfoFrame(view, Resources.MEDIA_INFO);
				}
			} else if (command.equals(Resources.ABOUT))
			{
				//new AboutDlg(view);
			}
		}
	}
	
	class MediaFileFilter extends FileFilter
    {
		@Override
		public boolean accept(File f) 
		{
			return f.isDirectory() || f.getName().toLowerCase().endsWith(".mp4") || f.getName().toLowerCase().endsWith(".avi"); // || f.getName().toLowerCase().endsWith(".mkv");
		}

		@Override
		public String getDescription() 
		{
			return "*.mp4; *.avi files";
		}
    }
}