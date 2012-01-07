package SJmp3.Utils.wavANDflac.wav2flac;

import SJmp3.SJmp3gui;
import java.io.File;
import javaFlacEncoder.FLAC_FileEncoder;

public class WavToFlac extends javax.swing.JDialog {

    public WavToFlac(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(SJmp3gui.frame);
        this.bConvert.setEnabled(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        tfWavIn = new javax.swing.JTextField();
        bSelectIn = new javax.swing.JButton();
        jToolBar2 = new javax.swing.JToolBar();
        jLabel2 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        tfFlacOut = new javax.swing.JTextField();
        bSelectOut = new javax.swing.JButton();
        bConvert = new javax.swing.JButton();
        bCancel = new javax.swing.JButton();
        lstatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Wav to Flac Converter");

        jToolBar1.setBorder(javax.swing.BorderFactory.createTitledBorder("Select Input Wave File"));
        jToolBar1.setFloatable(false);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/WAV-plum-24.png"))); // NOI18N
        jToolBar1.add(jLabel1);
        jToolBar1.add(jSeparator1);
        jToolBar1.add(tfWavIn);

        bSelectIn.setText("Select");
        bSelectIn.setFocusable(false);
        bSelectIn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bSelectIn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bSelectIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSelectInActionPerformed(evt);
            }
        });
        jToolBar1.add(bSelectIn);

        jToolBar2.setBorder(javax.swing.BorderFactory.createTitledBorder("Select Output Flac File"));
        jToolBar2.setFloatable(false);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJmp3/img/24x24/FLAC-black-24.png"))); // NOI18N
        jToolBar2.add(jLabel2);
        jToolBar2.add(jSeparator2);
        jToolBar2.add(tfFlacOut);

        bSelectOut.setText("Select");
        bSelectOut.setFocusable(false);
        bSelectOut.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bSelectOut.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bSelectOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSelectOutActionPerformed(evt);
            }
        });
        jToolBar2.add(bSelectOut);

        bConvert.setText("Convert");
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

        lstatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lstatus.setText("Info");
        lstatus.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bConvert)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lstatus, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bCancel)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lstatus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(bConvert)
                        .addComponent(bCancel)))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void bSelectInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSelectInActionPerformed
        SelectInWav.Select();
        tfFlacOut.setText(tfWavIn.getText()+".flac");
        if (!tfWavIn.getText().isEmpty()&!tfFlacOut.getText().isEmpty())
           bConvert.setEnabled(true);        
    }//GEN-LAST:event_bSelectInActionPerformed

    private void bSelectOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSelectOutActionPerformed
        SelectOutFlac.Select();
        if (!tfWavIn.getText().isEmpty()&!tfFlacOut.getText().isEmpty())
           bConvert.setEnabled(true);        
    }//GEN-LAST:event_bSelectOutActionPerformed

    private void bCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelActionPerformed
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_bCancelActionPerformed

    private void bConvertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bConvertActionPerformed
        if (tfWavIn.getText().isEmpty()||tfFlacOut.getText().isEmpty())
            { 
                lstatus.setText("Select Files Please !"); 
            }
        else
            {
                lstatus.setText("Please wait ...");
                this.repaint();
                FLAC_FileEncoder flacEncoder = new FLAC_FileEncoder();
                File inputFile = new File(tfWavIn.getText());
                File outputFile = new File(tfFlacOut.getText()); 
                flacEncoder.encode(inputFile, outputFile);         
                lstatus.setText("Successfull !");                
            }
    }//GEN-LAST:event_bConvertActionPerformed

    public static void goConverter() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                WavToFlac dialog = new WavToFlac(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton bConvert;
    private javax.swing.JButton bSelectIn;
    private javax.swing.JButton bSelectOut;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JLabel lstatus;
    public static javax.swing.JTextField tfFlacOut;
    public static javax.swing.JTextField tfWavIn;
    // End of variables declaration//GEN-END:variables
}
