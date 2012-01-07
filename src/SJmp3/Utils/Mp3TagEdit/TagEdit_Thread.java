package SJmp3.Utils.Mp3TagEdit;
import SJmp3.Actions;
import SJmp3.SJmp3gui;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.UnsupportedLookAndFeelException;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class TagEdit_Thread extends Thread {
    public TagEdit_Ext lu;
    public TagEdit_Thread () {  start();  }
    @Override
    public void run () {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {                 
                    lu=new TagEdit_Ext("Mp3 tag Editor");
                } catch (Exception ex) {
                    Logger.getLogger(TagEdit_Thread.class.getName()).log(Level.SEVERE, null, ex);
                }
                lu.setEnabled(false);
                lu.setVisible(false);                
                try 
                {
                    javax.swing.UIManager.setLookAndFeel(Actions.currentLAF);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(TagEdit_Thread.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(TagEdit_Thread.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(TagEdit_Thread.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(TagEdit_Thread.class.getName()).log(Level.SEVERE, null, ex);
                }
                SwingUtilities.updateComponentTreeUI(lu);
                //lu.pack(); 
                //ImageIcon icone = new ImageIcon(getClass().getResource("/SJmp3/img/USSR-16.png"));
                lu.setIconImage(SJmp3gui.frame.FrameIcon.getImage());        
                lu.setTitle("Mp3 tag Editor");
                lu.setSize(700,350);
                lu.setLocationRelativeTo(SJmp3gui.frame);
                lu.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                //lu.setLocation(250,200); 
                //lu.setResizable(false);
                lu.setEnabled(true);        
                lu.setVisible(true);                  
            }
        }); 
    }        
}
