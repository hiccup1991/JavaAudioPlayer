package SJmp3.Utils.Mp3SplitterByTime;
import SJmp3.Actions;
import SJmp3.SJmp3gui;
//import static SJmp3.Splitter.Splitter_ExtMainFrame.jbClose;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.UnsupportedLookAndFeelException;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
public class Splitter_Thread extends Thread {
    public Splitter_ExtMain opa;
    public java.awt.event.ActionEvent evt;
    public Splitter_Thread () {  start();  }
    @Override
    public void run () {
        String[] args={};
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try { opa=new Splitter_ExtMain(); } catch (NullPointerException ne) {}
                try {
                    opa.main(args);
                    //opa.mf.add(jbClose);
                    opa.mf.setSize(510,200);
                    opa.mf.setResizable(false);
                    opa.mf.setLocationRelativeTo(SJmp3gui.frame);
                    opa.mf.setVisible(true);
                    opa.mf.setEnabled(true);                    
                    opa.mf.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                    opa.mf.setTitle("Mp3 Splitter by Time   ( exit button = SJmp3 exit )"); //  ( Exit Button = SJmp3 Exit ! )
                    //ImageIcon icone = new ImageIcon(getClass().getResource("/SJmp3/img/USSR-16.png"));
                    opa.mf.setIconImage(SJmp3gui.frame.FrameIcon.getImage());
                    //System.out.println(opa.mf.getToolkit().);
                } catch (Exception ex) {
                    Logger.getLogger(Splitter_Thread.class.getName()).log(Level.SEVERE, null, ex);
                }                
                try {
                    javax.swing.UIManager.setLookAndFeel(Actions.currentLAF);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Splitter_Thread.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(Splitter_Thread.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(Splitter_Thread.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(Splitter_Thread.class.getName()).log(Level.SEVERE, null, ex);
                }
                SwingUtilities.updateComponentTreeUI(opa.mf);
                opa.mf.jbClose.setVisible(false);
                opa.mf.jbClose.setText("opa");
                //opa.mf.jbCloseActionPerformed(evt);
            }
        }); 
    }    
}