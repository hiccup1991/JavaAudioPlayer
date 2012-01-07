package SJmp3.LoadSave;

import SJmp3.Actions;
import SJmp3.Cfg.ProxyCfg;
import SJmp3.SJmp3gui;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class LoadURL extends javax.swing.JDialog {
    
    public LoadURL(java.awt.Frame parent, boolean modal) 
    {
        super(parent, modal);
        initComponents();
        //ImageIcon icone = new ImageIcon(getClass().getResource("/SJmp3/img/USSR-16.png"));
        this.setIconImage(SJmp3gui.frame.FrameIcon.getImage());  
        this.setLocationRelativeTo(SJmp3gui.frame);
        //this.jLabel1.setText("Input URL please, for example:\n"
        //                   + "http://www.foo.ru/a.mp3 or \n"
        //                   + "ftp://ftp.bar.ru/b.mp3 or mp3-RadioStation:\n"
        //                   + "http://icecast.sibinformburo.cdnvideo.ru:8000/dipolfm");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        tfURL = new javax.swing.JTextField();
        bUrlOk = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        bProxyCfg = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Open URL/RadioStation");
        setModal(true);
        setResizable(false);

        jLabel1.setText("<html>Input <b>URL</b> please, for example:<br>http://www.foo.ru/a.mp3  or  <br>ftp://ftp.bar.ru/b.wav or <b>RadioStation:</b><br>http://icecast.sibinformburo.cdnvideo.ru:8000/dipolfm<br>( Support formats: *.mp1, *.mp2, *.mp3, *.mpga, *.wav )</html>");
        jLabel1.setToolTipText("Support Anonymous FTP");

        bUrlOk.setText("OK");
        bUrlOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUrlOkActionPerformed(evt);
            }
        });

        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        bProxyCfg.setText("Proxy-Server Settings");
        bProxyCfg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bProxyCfgActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tfURL)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bUrlOk, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(bProxyCfg, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(tfURL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bUrlOk)
                    .addComponent(bProxyCfg)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        //Actions.useProxy="false";
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void bUrlOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUrlOkActionPerformed
        String bufer;
        int index;
        try 
         {
         bufer=tfURL.getText();
         if (bufer.toLowerCase().startsWith("http://")||bufer.toLowerCase().startsWith("ftp://")||bufer.toLowerCase().startsWith("https://")) 
          {
            index=SJmp3gui.currentLST.getSelectedIndex();
            LoadFileMp3.putf=bufer;
            Actions.currentList3m.add(LoadFileMp3.putf);
            //SJmp3gui.LST.setListData(Actions.zero);
            Actions.List3mToBuferList();
            SJmp3gui.currentLST.setListData(Actions.currentBuferList);
            SJmp3gui.currentLST.setSelectedIndex(index);
            this.setVisible(false);
            this.dispose();
            ///////////////////////////
          }  
         else
          {
            JOptionPane.showMessageDialog(null,"Must be start with ftp://, http://, https://, ! Wrong URL !","Error !",JOptionPane.ERROR_MESSAGE);
            Actions.urlFlag=false;
            LoadFileMp3.putf="";
          }
        } catch (NullPointerException nn) {}
    }//GEN-LAST:event_bUrlOkActionPerformed

    private void bProxyCfgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bProxyCfgActionPerformed
        ProxyCfg pc=new ProxyCfg(null,true);
        pc.setVisible(true);
    }//GEN-LAST:event_bProxyCfgActionPerformed

    public static void goURL(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                LoadURL dialog = new LoadURL(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bProxyCfg;
    public static javax.swing.JButton bUrlOk;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    public static javax.swing.JTextField tfURL;
    // End of variables declaration//GEN-END:variables
}
