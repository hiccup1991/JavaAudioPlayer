package SJmp3;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class About extends javax.swing.JDialog {

    public static final int RET_CANCEL = 0;
    public static final int RET_OK = 1;

    public About(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(SJmp3gui.frame);
        TAabout.setText(
            "SJmp3 - Free Cross-Platform Solo Java audio player with support:\n"+
            "m3u playlist editor, id3tag editor, over 35 skins, audio-mixer,\n"+
            "wav-to-mp3 and mp3-to-wav converter, mp3 splitter by time/size,\n"+
            "up to 7 playlist at the same time, different playing modes,\n"+
            "network stream audio over HTTP/HTTPS/Anonymous FTP with/without\n"+ 
            "Proxy-Server, user settings save, Internet Radio support,\n"+ 
            "seek by slider using my native seeking algorithm, not used JMF,\n"+ 
            "Forward/Backward, Next/Previous, Play/Pause/Stop track actions,\n"+
            "recursive search audio-files in directories tree, File/Stream Info.\n"+ 
            "Supported formats: *.mp1,*.mp2,*.mp3,*.mpga,*.wav. Need JRE 1.8.\n"+                    
            "Successfully tested on Windows/Linux 32/64 bit.\n"+ 
            "Create by Roman Koldaev, Saratov city, Russia");
        // Close the dialog when Esc is pressed
        String cancelName = "cancel";
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
        ActionMap actionMap = getRootPane().getActionMap();
        actionMap.put(cancelName, new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                doClose(RET_CANCEL);
            }
        });
    }

    public int getReturnStatus() {
        return returnStatus;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        okButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TAabout = new javax.swing.JTextArea();
        jToolBar4 = new javax.swing.JToolBar();
        jLabel1 = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        jToolBar2 = new javax.swing.JToolBar();
        jLabel3 = new javax.swing.JLabel();
        bMail = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jToolBar3 = new javax.swing.JToolBar();
        jLabel2 = new javax.swing.JLabel();
        bHomepage = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("About Solo Java sound player");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        TAabout.setEditable(false);
        TAabout.setColumns(20);
        TAabout.setRows(5);
        TAabout.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jScrollPane1.setViewportView(TAabout);

        jToolBar4.setFloatable(false);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/sjmp3_main.png"))); // NOI18N
        jToolBar4.add(jLabel1);

        jToolBar1.setBorder(javax.swing.BorderFactory.createTitledBorder("FeedBack"));
        jToolBar1.setFloatable(false);
        jToolBar1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jToolBar2.setFloatable(false);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/open_mail_sh.png"))); // NOI18N
        jLabel3.setText("E-Mail: ");
        jToolBar2.add(jLabel3);

        bMail.setText("<html><a href=\"mailto:harp07@mail.ru\">harp07@mail.ru</a></html>");
        bMail.setActionCommand("<html><a href=\"mailto:harp07@mail.ru\">mailto:harp07@mail.ru</a></html>");
        bMail.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        bMail.setFocusable(false);
        bMail.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        bMail.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bMail.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bMail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bMailActionPerformed(evt);
            }
        });
        jToolBar2.add(bMail);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/clipboard_plus.png"))); // NOI18N
        jButton3.setToolTipText("Copy E-Mail address to ClipBoard");
        jButton3.setFocusPainted(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton3);

        jToolBar1.add(jToolBar2);
        jToolBar1.add(jSeparator1);

        jToolBar3.setFloatable(false);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/home.png"))); // NOI18N
        jLabel2.setText("Home: ");
        jToolBar3.add(jLabel2);

        bHomepage.setText("<html><a href=\"http://sjmp3.sourceforge.net\">http://sjmp3.sourceforge.net</a></html>");
        bHomepage.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        bHomepage.setFocusable(false);
        bHomepage.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        bHomepage.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bHomepage.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bHomepage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bHomepageActionPerformed(evt);
            }
        });
        jToolBar3.add(bHomepage);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/clipboard_plus.png"))); // NOI18N
        jButton4.setToolTipText("Copy URL to ClipBoard");
        jButton4.setFocusPainted(false);
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar3.add(jButton4);

        jToolBar1.add(jToolBar3);

        jToolBar4.add(jToolBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jToolBar4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1)))
                    .addComponent(okButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToolBar4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(okButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getRootPane().setDefaultButton(okButton);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        doClose(RET_CANCEL);
    }//GEN-LAST:event_closeDialog

    private void bMailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bMailActionPerformed
        Mail_Url.goMAIL("mailto:harp07@mail.ru");
    }//GEN-LAST:event_bMailActionPerformed

    private void bHomepageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHomepageActionPerformed
        Mail_Url.goURL("http://sjmp3.sourceforge.net");
    }//GEN-LAST:event_bHomepageActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Actions.CopyToClipBoard("harp07@mail.ru");
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        Actions.CopyToClipBoard("http://sjmp3.sourceforge.net");
    }//GEN-LAST:event_jButton4ActionPerformed

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        doClose(RET_OK);
    }//GEN-LAST:event_okButtonActionPerformed
    
    private void doClose(int retStatus) {
        returnStatus = retStatus;
        setVisible(false);
        dispose();
    }

    public static void aboutRun() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                About dialog = new About(new javax.swing.JFrame(), true);
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
    public static javax.swing.JTextArea TAabout;
    private javax.swing.JButton bHomepage;
    private javax.swing.JButton bMail;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JToolBar jToolBar4;
    private javax.swing.JButton okButton;
    // End of variables declaration//GEN-END:variables

    private int returnStatus = RET_CANCEL;
}
