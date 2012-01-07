package SJmp3.Utils.wavANDflac.wavIflac;

import SJmp3.SJmp3gui;
import static SJmp3.Utils.wavANDflac.wavIflac.SelectInFiles.inFiles;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaFlacEncoder.FLAC_FileEncoder;
import org.kc7bfi.jflac.apps.Decoder;

public class WavAndFlac extends javax.swing.JDialog {
    
    public static Vector<String> ListFiles = new Vector<String>();
    public static String [] zeroString={};
    public static File [] zeroFile={};
    public static String sameOutFolder="true";

    public WavAndFlac(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(SJmp3gui.frame);
        if (sameOutFolder.equals("true"))
            {
                checkOut.setSelected(true);
                tbOut.setEnabled(false);
                tfOut.setEnabled(false);
                bSelectOut.setEnabled(false);
            }
        else
            {
                checkOut.setSelected(false);
                tbOut.setEnabled(true);
                tfOut.setEnabled(true);
                bSelectOut.setEnabled(true);                
            }         
        //this.bConvert.setEnabled(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tbIn = new javax.swing.JToolBar();
        bOpenFiles = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        bClear = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jLabel1 = new javax.swing.JLabel();
        comboSelectType = new javax.swing.JComboBox<>();
        tbOut = new javax.swing.JToolBar();
        tfOut = new javax.swing.JTextField();
        bSelectOut = new javax.swing.JButton();
        bConvert = new javax.swing.JButton();
        bCancel = new javax.swing.JButton();
        lInfo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        checkOut = new javax.swing.JCheckBox();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mOpenFiles = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Wav-to-Flac and Flac-to-Wav Converter");

        tbIn.setBorder(javax.swing.BorderFactory.createTitledBorder("Select Input "));
        tbIn.setFloatable(false);

        bOpenFiles.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/Actions-document-open-icon.png"))); // NOI18N
        bOpenFiles.setToolTipText("Open Files");
        bOpenFiles.setFocusable(false);
        bOpenFiles.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bOpenFiles.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bOpenFiles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bOpenFilesActionPerformed(evt);
            }
        });
        tbIn.add(bOpenFiles);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/Folder-Generic-Green-24.png"))); // NOI18N
        jButton2.setToolTipText("Open Folder");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbIn.add(jButton2);

        bClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/edit_clear.png"))); // NOI18N
        bClear.setToolTipText("Clear List");
        bClear.setFocusable(false);
        bClear.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bClear.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bClearActionPerformed(evt);
            }
        });
        tbIn.add(bClear);
        tbIn.add(jSeparator2);

        jLabel1.setText(" Select Converter Type: ");
        tbIn.add(jLabel1);

        comboSelectType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Wav To Flac", "Flac To Wav" }));
        tbIn.add(comboSelectType);

        tbOut.setBorder(javax.swing.BorderFactory.createTitledBorder("Select Output Folder"));
        tbOut.setFloatable(false);
        tbOut.add(tfOut);

        bSelectOut.setText("Select");
        bSelectOut.setFocusable(false);
        bSelectOut.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bSelectOut.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bSelectOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSelectOutActionPerformed(evt);
            }
        });
        tbOut.add(bSelectOut);

        bConvert.setText("Convert All");
        bConvert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bConvertActionPerformed(evt);
            }
        });

        bCancel.setText("Cancel");
        bCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelActionPerformed(evt);
            }
        });

        lInfo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lInfo.setText("Info");
        lInfo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jList1);

        checkOut.setText("Save at the same folder");
        checkOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkOutActionPerformed(evt);
            }
        });

        jMenu1.setText("File");

        mOpenFiles.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/16x16/Actions-document-open-16.png"))); // NOI18N
        mOpenFiles.setText("Open Files");
        mOpenFiles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mOpenFilesActionPerformed(evt);
            }
        });
        jMenu1.add(mOpenFiles);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tbIn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tbOut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bConvert)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lInfo, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bCancel))
                    .addComponent(checkOut, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tbIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(checkOut)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tbOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bConvert)
                    .addComponent(lInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bCancel))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void bSelectOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSelectOutActionPerformed
        SelectOutFolder.Select();
    }//GEN-LAST:event_bSelectOutActionPerformed

    private void bCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelActionPerformed
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_bCancelActionPerformed

    private void bConvertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bConvertActionPerformed
        for (int i=0; i < inFiles.length; i++)
            {
              if (inFiles[i].getPath().toLowerCase().endsWith(".wav"))
              {
                FLAC_FileEncoder flacEncoder = new FLAC_FileEncoder();
                File inputFile = new File(inFiles[i].getPath());
                File outputFile = new File(inFiles[i].getPath()+".flac"); 
                flacEncoder.encode(inputFile, outputFile);                  
              }
              else
              {
                Decoder flac2wav=new Decoder();
                try {        
                    flac2wav.decode(inFiles[i].getPath(), inFiles[i].getPath()+".wav");
                } catch (IOException ex) {
                    Logger.getLogger(WavAndFlac.class.getName()).log(Level.SEVERE, null, ex);
                }                  
              }
            }
    }//GEN-LAST:event_bConvertActionPerformed

    private void bOpenFilesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bOpenFilesActionPerformed
        SelectInFiles.Select();
    }//GEN-LAST:event_bOpenFilesActionPerformed

    private void bClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bClearActionPerformed
        jList1.removeAll();
        jList1.setListData(zeroString);
        inFiles=zeroFile;
    }//GEN-LAST:event_bClearActionPerformed

    private void checkOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkOutActionPerformed
        if (sameOutFolder.equals("false"))
            {
                sameOutFolder="true";
                checkOut.setSelected(true);
                tbOut.setEnabled(false);
                tfOut.setEnabled(false);
                bSelectOut.setEnabled(false);
            }
        else
            {
                sameOutFolder="false";
                checkOut.setSelected(false);
                tbOut.setEnabled(true);
                tfOut.setEnabled(true);
                bSelectOut.setEnabled(true);                
            }            
    }//GEN-LAST:event_checkOutActionPerformed

    private void mOpenFilesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mOpenFilesActionPerformed
        SelectInFiles.Select();
    }//GEN-LAST:event_mOpenFilesActionPerformed

    public static void goConverter() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                WavAndFlac dialog = new WavAndFlac(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton bCancel;
    private javax.swing.JButton bClear;
    private javax.swing.JButton bConvert;
    private javax.swing.JButton bOpenFiles;
    private javax.swing.JButton bSelectOut;
    public static javax.swing.JCheckBox checkOut;
    public static javax.swing.JComboBox<String> comboSelectType;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    public static javax.swing.JList<String> jList1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JLabel lInfo;
    private javax.swing.JMenuItem mOpenFiles;
    private javax.swing.JToolBar tbIn;
    private javax.swing.JToolBar tbOut;
    public static javax.swing.JTextField tfOut;
    // End of variables declaration//GEN-END:variables
}
