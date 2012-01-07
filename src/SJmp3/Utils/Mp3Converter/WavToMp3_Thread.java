package SJmp3.Utils.Mp3Converter;
import SJmp3.Actions;
import SJmp3.SJmp3gui;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.UnsupportedLookAndFeelException;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import org.pushingpixels.substance.api.skin.*;
public class WavToMp3_Thread extends Thread {
    //public WavToMp3_Ext lu;
    public ui.LameUI lu;    
    public WavToMp3_Thread () {  start();  }
    @Override
    public void run () {
        //String[] args={};
        //SwingUtilities.invokeLater
        //java.awt.EventQueue.invokeLater(
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //try { lu=new WavToMp3_Ext(); } catch (NullPointerException ne) {}
                try { lu=new ui.LameUI(); } catch (NullPointerException ne) {}                
                lu.setEnabled(false);
                lu.setVisible(false);                
                try {
                    //javax.swing.UIManager.setLookAndFeel(new SubstanceGraphiteAquaLookAndFeel());
                    javax.swing.UIManager.setLookAndFeel(Actions.currentLAF);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(WavToMp3_Thread.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(WavToMp3_Thread.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(WavToMp3_Thread.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(WavToMp3_Thread.class.getName()).log(Level.SEVERE, null, ex);
                }
                SwingUtilities.updateComponentTreeUI(lu);
                //lu.pack(); 
                //ImageIcon icone = new ImageIcon(getClass().getResource("/SJmp3/img/USSR-16.png"));
                lu.setIconImage(SJmp3gui.frame.FrameIcon.getImage());        
                lu.setTitle("Wav-to-Mp3 and Mp3-to-Wav Converter");
                lu.setSize(700,450);
                lu.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                //lu.setLocation(200,200); 
                lu.setLocationRelativeTo(SJmp3gui.frame);
                //lu.setResizable(false);
                lu.setEnabled(true);        
                lu.setVisible(true);                  
            }
        }); 
        //this.lu.main(args);
    }    
}
