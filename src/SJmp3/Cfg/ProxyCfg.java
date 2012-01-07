package SJmp3.Cfg;
import SJmp3.Actions;
import SJmp3.SJmp3gui;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
public class ProxyCfg extends javax.swing.JDialog {
    public static final int RET_CANCEL = 0;
    public static final int RET_OK = 1;
    public ProxyCfg(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        //ImageIcon icone = new ImageIcon(getClass().getResource("/SJmp3/img/USSR-16.png"));
        this.setIconImage(SJmp3gui.frame.FrameIcon.getImage());
        this.setLocationRelativeTo(SJmp3gui.frame);
        if (Actions.useProxy.equals("true"))
         {
           this.tfProxyIP.setEnabled(true); 
           this.tfProxyPort.setEnabled(true);           
           this.pfProxyPassw.setEnabled(true);
           this.tfProxyLogin.setEnabled(true);  
           this.lbProxyIP.setEnabled(true); 
           this.lbProxyPort.setEnabled(true);           
           this.lbProxyPassw.setEnabled(true);
           this.lbProxyLogin.setEnabled(true); 
           this.bUseProxy.setSelected(true);
           this.pfRetypePassw.setEnabled(true);  
           this.lbRetypePassw.setEnabled(true);           
           this.tfProxyIP.setText(Actions.proxyIP); 
           this.tfProxyPort.setText(Actions.proxyPort);  
           this.pfProxyPassw.setText(Actions.proxyPassw); 
           this.tfProxyLogin.setText(Actions.proxyLogin);
           this.pfRetypePassw.setText(Actions.proxyPassw);           
         }
        else
         {
           this.tfProxyIP.setEnabled(false); 
           this.tfProxyPort.setEnabled(false);           
           this.pfProxyPassw.setEnabled(false);
           this.tfProxyLogin.setEnabled(false);   
           this.lbProxyIP.setEnabled(false);
           this.lbProxyPort.setEnabled(false);           
           this.lbProxyPassw.setEnabled(false);
           this.lbProxyLogin.setEnabled(false);
           this.bUseProxy.setSelected(false); 
           this.pfRetypePassw.setEnabled(false); 
           this.lbRetypePassw.setEnabled(false);           
           this.tfProxyIP.setText(Actions.proxyIP); 
           this.tfProxyPort.setText(Actions.proxyPort);  
           this.pfProxyPassw.setText(Actions.proxyPassw); 
           this.tfProxyLogin.setText(Actions.proxyLogin); 
           this.pfRetypePassw.setText(Actions.proxyPassw);           
         }                
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
        cancelButton = new javax.swing.JButton();
        ProxyPanel = new javax.swing.JPanel();
        lbProxyIP = new javax.swing.JLabel();
        lbProxyPort = new javax.swing.JLabel();
        lbProxyLogin = new javax.swing.JLabel();
        lbProxyPassw = new javax.swing.JLabel();
        tfProxyIP = new javax.swing.JTextField();
        tfProxyPort = new javax.swing.JTextField();
        tfProxyLogin = new javax.swing.JTextField();
        lbRetypePassw = new javax.swing.JLabel();
        pfRetypePassw = new javax.swing.JPasswordField();
        pfProxyPassw = new javax.swing.JPasswordField();
        bUseProxy = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Proxy-Server Settings");
        setResizable(false);
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

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        ProxyPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Proxy-Server Settings"));

        lbProxyIP.setText("Proxy IP address :");

        lbProxyPort.setText("Proxy Port :");

        lbProxyLogin.setText("Proxy Login :");

        lbProxyPassw.setText("Proxy Password :");

        lbRetypePassw.setText("Retype Password:");

        javax.swing.GroupLayout ProxyPanelLayout = new javax.swing.GroupLayout(ProxyPanel);
        ProxyPanel.setLayout(ProxyPanelLayout);
        ProxyPanelLayout.setHorizontalGroup(
            ProxyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProxyPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ProxyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lbRetypePassw, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbProxyLogin, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbProxyPort, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbProxyPassw, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbProxyIP, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(ProxyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tfProxyPort, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tfProxyLogin)
                    .addComponent(tfProxyIP, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pfRetypePassw)
                    .addComponent(pfProxyPassw))
                .addContainerGap())
        );
        ProxyPanelLayout.setVerticalGroup(
            ProxyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProxyPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ProxyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbProxyIP)
                    .addComponent(tfProxyIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ProxyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbProxyPort)
                    .addComponent(tfProxyPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ProxyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbProxyLogin)
                    .addComponent(tfProxyLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ProxyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbProxyPassw)
                    .addComponent(pfProxyPassw, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ProxyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbRetypePassw)
                    .addComponent(pfRetypePassw, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        bUseProxy.setText("Use Proxy");
        bUseProxy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUseProxyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bUseProxy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ProxyPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cancelButton)
                        .addGap(0, 61, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelButton, okButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bUseProxy)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ProxyPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(okButton)
                    .addComponent(cancelButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getRootPane().setDefaultButton(okButton);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        if (Actions.useProxy.equals("false")) doClose(RET_OK);
        else 
        {  
         Actions.proxyIP=tfProxyIP.getText();
         Actions.proxyPort=tfProxyPort.getText();
         Actions.proxyLogin=tfProxyLogin.getText();             
         if ((pfProxyPassw.getText().equals(pfRetypePassw.getText()))&(pfProxyPassw.getText().length()>0)&(!Actions.proxyIP.isEmpty())&(!Actions.proxyPort.isEmpty())&(!Actions.proxyLogin.isEmpty()))
         {
          Actions.proxyPassw=pfProxyPassw.getText();
          doClose(RET_OK);          
         }
         else 
         {
            if (Actions.proxyIP.isEmpty()) JOptionPane.showMessageDialog(null,"Enter IP address !","Error !",JOptionPane.ERROR_MESSAGE);
            if (Actions.proxyPort.isEmpty()) JOptionPane.showMessageDialog(null,"Enter Port !","Error !",JOptionPane.ERROR_MESSAGE);  
            if (Actions.proxyLogin.isEmpty()) JOptionPane.showMessageDialog(null,"Enter Login !","Error !",JOptionPane.ERROR_MESSAGE); 
            if (pfProxyPassw.getText().length()==0) JOptionPane.showMessageDialog(null,"Enter Password !","Error !",JOptionPane.ERROR_MESSAGE);            
            if (!pfProxyPassw.getText().equals(pfRetypePassw.getText())) 
             {
                JOptionPane.showMessageDialog(null,"Retype Password incorrect !","Error !",JOptionPane.ERROR_MESSAGE);
                pfProxyPassw.setText("");
                pfRetypePassw.setText("");                 
             }
         }
        //pfProxyPassw.getPassword().toString();
        }
    }//GEN-LAST:event_okButtonActionPerformed
    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
         if ((!pfProxyPassw.getText().equals(pfRetypePassw.getText()))||(pfProxyPassw.getText().length()==0)||(Actions.proxyIP.isEmpty())||(Actions.proxyPort.isEmpty())||(Actions.proxyLogin.isEmpty()))
         {
            Actions.useProxy="false";
         }
        doClose(RET_CANCEL);
    }//GEN-LAST:event_cancelButtonActionPerformed
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
         if ((!pfProxyPassw.getText().equals(pfRetypePassw.getText()))||(pfProxyPassw.getText().length()==0)||(Actions.proxyIP.isEmpty())||(Actions.proxyPort.isEmpty())||(Actions.proxyLogin.isEmpty()))
         {
            Actions.useProxy="false";
         }
        doClose(RET_CANCEL);
    }//GEN-LAST:event_closeDialog
    private void bUseProxyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUseProxyActionPerformed
        if (Actions.useProxy.equals("false"))
        {
            Actions.useProxy="true";
            this.tfProxyIP.setEnabled(true);
            this.tfProxyPort.setEnabled(true);
            this.pfProxyPassw.setEnabled(true);
            this.tfProxyLogin.setEnabled(true);
            this.lbProxyIP.setEnabled(true);
            this.lbProxyPort.setEnabled(true);
            this.lbProxyPassw.setEnabled(true);
            this.lbProxyLogin.setEnabled(true);
            this.pfRetypePassw.setEnabled(true);  
            this.lbRetypePassw.setEnabled(true);
        }
        else
        {
            Actions.useProxy="false";
            this.tfProxyIP.setEnabled(false);
            this.tfProxyPort.setEnabled(false);
            this.pfProxyPassw.setEnabled(false);
            this.tfProxyLogin.setEnabled(false);
            this.lbProxyIP.setEnabled(false);
            this.lbProxyPort.setEnabled(false);
            this.lbProxyPassw.setEnabled(false);
            this.lbProxyLogin.setEnabled(false);
            this.pfRetypePassw.setEnabled(false); 
            this.lbRetypePassw.setEnabled(false);            
        }
    }//GEN-LAST:event_bUseProxyActionPerformed
    private void doClose(int retStatus) {
        returnStatus = retStatus;
        setVisible(false);
        dispose();
    }
    public static void proxyCFGgo(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Metal".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ProxyCfg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProxyCfg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProxyCfg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProxyCfg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ProxyCfg dialog = new ProxyCfg(new javax.swing.JFrame(), true);
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
    public static javax.swing.JPanel ProxyPanel;
    public static javax.swing.JCheckBox bUseProxy;
    private javax.swing.JButton cancelButton;
    public static javax.swing.JLabel lbProxyIP;
    public static javax.swing.JLabel lbProxyLogin;
    public static javax.swing.JLabel lbProxyPassw;
    public static javax.swing.JLabel lbProxyPort;
    public static javax.swing.JLabel lbRetypePassw;
    private javax.swing.JButton okButton;
    public static javax.swing.JPasswordField pfProxyPassw;
    public static javax.swing.JPasswordField pfRetypePassw;
    public static javax.swing.JTextField tfProxyIP;
    public static javax.swing.JTextField tfProxyLogin;
    public static javax.swing.JTextField tfProxyPort;
    // End of variables declaration//GEN-END:variables
    private int returnStatus = RET_CANCEL;
}
