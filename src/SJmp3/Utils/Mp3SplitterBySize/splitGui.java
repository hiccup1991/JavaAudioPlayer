package SJmp3.Utils.Mp3SplitterBySize;

import SJmp3.Filters.MFileFilter;
import SJmp3.SJmp3gui;
import SJmp3.Utils.Mp3SplitterBySize.Splitter;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class splitGui extends javax.swing.JFrame implements Runnable {

    String fileToSplitName;
    int parts;
    File fileToSplit;

    public splitGui() {
        initComponents(); 
        //ImageIcon icone = SJmp3gui.frame.FrameIcon;
        this.setIconImage(SJmp3gui.frame.FrameIcon.getImage()); 
        this.setLocationRelativeTo(SJmp3gui.frame);        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        noteLabel = new javax.swing.JLabel();
        splitButton = new javax.swing.JButton();
        resultLabel = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        fileNameLabel = new javax.swing.JLabel();
        fileName = new javax.swing.JTextField();
        browseButton = new javax.swing.JButton();
        jToolBar4 = new javax.swing.JToolBar();
        jToolBar2 = new javax.swing.JToolBar();
        noOfPartsLabel = new javax.swing.JLabel();
        noOfParts = new javax.swing.JTextField();
        jToolBar3 = new javax.swing.JToolBar();
        maxSizeLabel = new javax.swing.JLabel();
        maxSize = new javax.swing.JTextField();
        sizeSelect = new javax.swing.JComboBox();

        setTitle("Mp3 Splitter by Size");
        setResizable(false);

        noteLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        noteLabel.setText("Note: The Split Files Will Be Placed in the Same Directory");

        splitButton.setText("Split");
        splitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                splitButtonActionPerformed(evt);
            }
        });

        resultLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        resultLabel.setText("Click Split");

        jToolBar1.setBorder(javax.swing.BorderFactory.createTitledBorder("Select File:"));
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        fileNameLabel.setText("File: ");
        jToolBar1.add(fileNameLabel);
        jToolBar1.add(fileName);

        browseButton.setText("Browse");
        browseButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        browseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(browseButton);

        jToolBar4.setBorder(javax.swing.BorderFactory.createTitledBorder("Enter Size or Parts:"));
        jToolBar4.setFloatable(false);
        jToolBar4.setRollover(true);

        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);

        noOfPartsLabel.setText("Number Of Parts: ");
        jToolBar2.add(noOfPartsLabel);
        jToolBar2.add(noOfParts);

        jToolBar4.add(jToolBar2);

        jToolBar3.setFloatable(false);
        jToolBar3.setRollover(true);

        maxSizeLabel.setText("Max File Size: ");
        jToolBar3.add(maxSizeLabel);

        maxSize.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jToolBar3.add(maxSize);

        sizeSelect.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "GB", "MB", "KB" }));
        sizeSelect.setSelectedIndex(1);
        jToolBar3.add(sizeSelect);

        jToolBar4.add(jToolBar3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jToolBar4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(resultLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(noteLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(splitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(149, 149, 149))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jToolBar4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(splitButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(resultLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(noteLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleParent(this);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void browseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseButtonActionPerformed
        JFileChooser chooser= new JFileChooser();
        chooser.addChoosableFileFilter(new MFileFilter(".mp3"));
        chooser.addChoosableFileFilter(new MFileFilter(".MP3"));        
        chooser.setAcceptAllFileFilterUsed(false);
	switch (chooser.showDialog(SJmp3gui.frame, "Open File"))
	 {
	  case JFileChooser.APPROVE_OPTION:        
            //int returnVal=chooser.showOpenDialog(splitGui.this);
            fileToSplit=chooser.getSelectedFile();
            fileToSplitName=chooser.getName(fileToSplit);
            if (!(fileToSplitName.endsWith("mp3")||fileToSplitName.endsWith("MP3"))) 
               {
                JOptionPane.showMessageDialog(SJmp3gui.frame,"Not Mp3-file !","Error !",JOptionPane.ERROR_MESSAGE);
                fileToSplitName="";
                fileToSplit=null;
                break;
               }        
            fileName.setText(fileToSplitName);
	  case JFileChooser.CANCEL_OPTION: break;
	 }        
    }//GEN-LAST:event_browseButtonActionPerformed
    private void splitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_splitButtonActionPerformed
        if (fileToSplit!=null) 
          {
            Thread t=new Thread(this);
            t.start();      
          }
        else
          JOptionPane.showMessageDialog(SJmp3gui.frame,"Not Mp3-file !","Error !",JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_splitButtonActionPerformed
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new splitGui().setVisible(true);
            }
        });
    }
    public void run()
    {
        boolean partsBool=true;
        long maxSizeL=0;
        long sizeSelectL=1024;
        resultLabel.setText("Splitting..Please Wait..");
        try{
        if(!(noOfParts.getText() == null||noOfParts.getText().equals("")))
        {
            parts=Integer.parseInt(noOfParts.getText());
        }
        else
        {
            partsBool=false;
            if(sizeSelect.getSelectedIndex()==0)
            {
                sizeSelectL*=1024*1024;
            }
            else if(sizeSelect.getSelectedIndex()==1)
            {
                sizeSelectL*=1024;
            }
            maxSizeL=sizeSelectL*(Integer.parseInt(maxSize.getText()));
        }}
        finally{
            JOptionPane.showMessageDialog(SJmp3gui.frame, maxSizeL+"   "+parts);
            Splitter split=new Splitter(fileToSplit,parts,maxSizeL,partsBool);
            split.split();
            resultLabel.setText("Done..");}

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton browseButton;
    private javax.swing.JTextField fileName;
    private javax.swing.JLabel fileNameLabel;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JToolBar jToolBar4;
    private javax.swing.JTextField maxSize;
    private javax.swing.JLabel maxSizeLabel;
    private javax.swing.JTextField noOfParts;
    private javax.swing.JLabel noOfPartsLabel;
    private javax.swing.JLabel noteLabel;
    private javax.swing.JLabel resultLabel;
    private javax.swing.JComboBox sizeSelect;
    private javax.swing.JButton splitButton;
    // End of variables declaration//GEN-END:variables
}
