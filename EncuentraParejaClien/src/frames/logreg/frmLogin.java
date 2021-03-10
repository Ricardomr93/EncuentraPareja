package frames.logreg;

import frames.*;
import java.io.IOException;
import java.net.*;
import java.security.*;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import model.User;
import util.*;

/**
 *
 * @author Ricardo
 */
public final class frmLogin extends javax.swing.JFrame {

    private Socket servidor;
    private InetAddress dir;
    private PrivateKey clavePrivada;
    private PublicKey clavePublica;
    private PublicKey clavePubAjena;

    /**
     * Creates new form frmLogin
     */
    public frmLogin() {
        initComponents();
        generarKeyPair();
        try {
            dir = InetAddress.getLocalHost();
            servidor = new Socket(dir, 3500);
            lblError.setText("");
            mandRecPub();
        } catch (UnknownHostException e) {
        } catch (IOException ex) {
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlprin = new javax.swing.JPanel();
        pnlTit = new javax.swing.JPanel();
        lblTitloh = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lblPass = new javax.swing.JLabel();
        txtPass = new javax.swing.JPasswordField();
        btnSalir = new javax.swing.JButton();
        lblError = new javax.swing.JLabel();
        lblNoCuenta = new javax.swing.JLabel();
        lblRegistrar = new javax.swing.JLabel();
        btnRegis = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        pnlprin.setBackground(new java.awt.Color(44, 40, 40));
        pnlprin.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        pnlTit.setBackground(new java.awt.Color(59, 44, 133));

        lblTitloh.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lblTitloh.setForeground(new java.awt.Color(255, 255, 255));
        lblTitloh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitloh.setText("Login");

        javax.swing.GroupLayout pnlTitLayout = new javax.swing.GroupLayout(pnlTit);
        pnlTit.setLayout(pnlTitLayout);
        pnlTitLayout.setHorizontalGroup(
            pnlTitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTitLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitloh, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlTitLayout.setVerticalGroup(
            pnlTitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTitLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitloh, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblEmail.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblEmail.setForeground(new java.awt.Color(133, 207, 203));
        lblEmail.setText("Email:");

        txtEmail.setBackground(new java.awt.Color(44, 40, 40));
        txtEmail.setForeground(new java.awt.Color(133, 207, 203));
        txtEmail.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(33, 152, 151), 1, true));
        txtEmail.setMargin(new java.awt.Insets(0, 0, 0, 10));

        lblPass.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblPass.setForeground(new java.awt.Color(133, 207, 203));
        lblPass.setText("Constrase�a:");

        txtPass.setBackground(new java.awt.Color(44, 40, 40));
        txtPass.setForeground(new java.awt.Color(133, 207, 203));
        txtPass.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(33, 152, 151), 1, true));

        btnSalir.setBackground(new java.awt.Color(59, 44, 133));
        btnSalir.setForeground(new java.awt.Color(255, 255, 255));
        btnSalir.setText("Salir");
        btnSalir.setBorder(null);
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        lblError.setForeground(new java.awt.Color(255, 0, 0));
        lblError.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblError.setText("Email o contrase�a erroneos");

        lblNoCuenta.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        lblNoCuenta.setForeground(new java.awt.Color(133, 207, 203));
        lblNoCuenta.setText("Aun no tienes cuenta?");

        lblRegistrar.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        lblRegistrar.setForeground(new java.awt.Color(33, 152, 151));
        lblRegistrar.setText("Registrate");
        lblRegistrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblRegistrarMousePressed(evt);
            }
        });

        btnRegis.setBackground(new java.awt.Color(59, 44, 133));
        btnRegis.setForeground(new java.awt.Color(255, 255, 255));
        btnRegis.setText("Entrar");
        btnRegis.setBorder(null);
        btnRegis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlprinLayout = new javax.swing.GroupLayout(pnlprin);
        pnlprin.setLayout(pnlprinLayout);
        pnlprinLayout.setHorizontalGroup(
            pnlprinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlprinLayout.createSequentialGroup()
                .addComponent(pnlTit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(pnlprinLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlprinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlprinLayout.createSequentialGroup()
                        .addComponent(lblNoCuenta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlprinLayout.createSequentialGroup()
                        .addGroup(pnlprinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblError, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlprinLayout.createSequentialGroup()
                                .addGroup(pnlprinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblPass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pnlprinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtPass, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                                    .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                                    .addComponent(btnRegis, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(pnlprinLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlprinLayout.setVerticalGroup(
            pnlprinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlprinLayout.createSequentialGroup()
                .addComponent(pnlTit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addGroup(pnlprinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlprinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPass, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblError, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRegis, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlprinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNoCuenta)
                    .addComponent(lblRegistrar))
                .addGap(9, 9, 9)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlprin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlprin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnRegisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegisActionPerformed
        JTextField[] txt = {txtEmail, txtPass};
        if (UtilText.someTextEmpty(txt)) {
            lblError.setText("Alguno de los campos vac�os");
        } else {
            try {
                SealedObject so = UtilSec.encapsularObjeto(clavePubAjena, Constantes.LOGIN);//envia opcion
                UtilMsj.enviarObject(servidor, so);//manda 1 para logearse
                so = UtilSec.encapsularObjeto(clavePubAjena, createUser());//envia user
                UtilMsj.enviarObject(servidor, so);
                boolean valido = (Boolean) UtilMsj.recibirObjetoCifrado(servidor, clavePrivada);
                if (valido) {
                    User user = (User) UtilMsj.recibirObjetoCifrado(servidor, clavePrivada);
                    if (user.isActive()) {
                        frmMain main = new frmMain(servidor, clavePrivada, clavePublica, clavePubAjena, user);
                        main.setVisible(true);
                        this.setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(this, "Aun debe ser aprobado por un administrador");
                    }
                } else {
                    lblError.setText("Usuario o contrase�a erroneos");
                }
            } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | IllegalBlockSizeException | ClassNotFoundException | BadPaddingException ex) {
                System.out.println(ex.getMessage());
            }

        }
    }//GEN-LAST:event_btnRegisActionPerformed
    private User createUser() throws NoSuchAlgorithmException {
        return new User(null, txtEmail.getText(), UtilSec.hashPass(txtPass.getText()));
    }
    private void lblRegistrarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRegistrarMousePressed
        frmRegistro fr = new frmRegistro(servidor, clavePrivada, clavePublica, clavePubAjena, this);
        fr.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_lblRegistrarMousePressed
    private void generarKeyPair() {
        KeyPair kp = UtilSec.generarclavePar();
        clavePublica = kp.getPublic();
        clavePrivada = kp.getPrivate();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmLogin().setVisible(true);
            }
        });
    }

    public void mandRecPub() {
        //envia y recibe las publicas
        UtilMsj.enviarObject(servidor, clavePublica);
        clavePubAjena = (PublicKey) UtilMsj.recibirObjeto(servidor);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRegis;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblError;
    private javax.swing.JLabel lblNoCuenta;
    private javax.swing.JLabel lblPass;
    private javax.swing.JLabel lblRegistrar;
    private javax.swing.JLabel lblTitloh;
    private javax.swing.JPanel pnlTit;
    private javax.swing.JPanel pnlprin;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JPasswordField txtPass;
    // End of variables declaration//GEN-END:variables

}
