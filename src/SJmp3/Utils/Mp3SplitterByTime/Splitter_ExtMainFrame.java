package SJmp3.Utils.Mp3SplitterByTime;

import javax.swing.JButton;

public class Splitter_ExtMainFrame extends pl.highcom.mp3splitter.MainFrame {
    
    public static JButton jbClose;
    
    public Splitter_ExtMainFrame () {
        //super("OPA");
        this.jbClose=new JButton("ppp");
        //try {    
            this.jbClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCloseActionPerformed(evt);
            }
        });
        //}catch (NullPointerException nn) {}      
    }    
    //@Override
    public void jbCloseActionPerformed(java.awt.event.ActionEvent evt) {                                        
        this.setVisible(false);
        this.dispose(); 
    }

}
