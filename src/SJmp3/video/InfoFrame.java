package SJmp3.video;

import SJmp3.SJmp3gui;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

public class InfoFrame extends JFrame
{
	//private static final long serialVersionUID = 1348282999663841769L;
	
	private JEditorPane pane;
	
	public InfoFrame(View view, String title)
	{
		super(title);
		
		pane = new JEditorPane();
		pane.setContentType("text/html");
		HTMLEditorKit kit = new HTMLEditorKit();
		HTMLDocument doc = (HTMLDocument)kit.createDefaultDocument();
		pane.setDocument(doc);
		pane.setEditable(false);
		
		MediaInfo mediaInfo = new MediaInfo(view);
		pane.setText(mediaInfo.getInfo());
		
		Container content = getContentPane();
		content.add(pane);
		pack();
		setLocationRelativeTo(view);
		//setIconImage(Resources.java_coffe_32.getImage());
                setIconImage(SJmp3gui.frame.FrameIcon.getImage());
		setMinimumSize(new Dimension(400, 400));
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
}