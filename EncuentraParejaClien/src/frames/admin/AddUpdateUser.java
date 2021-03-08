/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frames.admin;

import java.io.IOException;
import java.net.Socket;
import java.security.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import model.User;
import util.*;

/**
 *
 * @author Ricardo
 */
public class AddUpdateUser extends javax.swing.JDialog {

    private Socket servidor;
    private PrivateKey privK;
    private PublicKey pubK;
    private PublicKey pubKAjena;
    private int opc;
    private JTextField[] txt;
    private ArrayList<User> uList;
    private int pos;

    /**
     * Creates new form AddUpdateUser
     */
    public AddUpdateUser(javax.swing.JDialog parent, boolean modal, Socket servidor, PrivateKey privK, PublicKey pubK, PublicKey pubKAjena, int opc, ArrayList<User> uList, int pos) {
        super(parent, modal);
        initComponents();
        this.servidor = servidor;
        this.privK = privK;
        this.pubK = pubK;
        this.pubKAjena = pubKAjena;
        this.opc = opc;
        this.lblError.setText("");
        this.txt = asignarJTextField();
        this.uList = uList;
        this.pos = pos;
        changeTitle();
    }

    private void rellenarDatos() {
        txtNom.setText(uList.get(pos).getName());
        txtEmail.setText(uList.get(pos).getEmail());
        chbActi.setSelected(uList.get(pos).isActive());
        chbAdmin.setSelected(uList.get(pos).isAdmin());
    }

    private void changeTitle() {
        //0 para insertar 1 para modificar
        if (opc == Constantes.OPC_INSERTAR) {
            lblTit.setText("A�adir Usuario");
            btnRegis.setText("A�adir");
        } else {
            lblTit.setText("Modificar Usuario");
            btnRegis.setText("Modificar");
            rellenarDatos();
        }
    }

    private JTextField[] asignarJTextField() {
        JTextField[] texts = {txtEmail, txtNom, txtPass};
        return texts;
    }

    private AddUpdateUser(JFrame jFrame, boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblNombre = new javax.swing.JLabel();
        txtNom = new javax.swing.JTextField();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lblPass = new javax.swing.JLabel();
        txtPass = new javax.swing.JPasswordField();
        lblActi = new javax.swing.JLabel();
        lbAdmin = new javax.swing.JLabel();
        chbActi = new javax.swing.JCheckBox();
        chbAdmin = new javax.swing.JCheckBox();
        btnRegis = new javax.swing.JButton();
        btnAtras = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        pnlTitulo = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        lblError = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(44, 40, 40));
        jPanel1.setForeground(new java.awt.Color(204, 204, 204));

        lblNombre.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblNombre.setForeground(new java.awt.Color(133, 207, 203));
        lblNombre.setText("Nombre:");

        txtNom.setBackground(new java.awt.Color(44, 40, 40));
        txtNom.setForeground(new java.awt.Color(133, 207, 203));
        txtNom.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(33, 152, 151), 1, true));
        txtNom.setMargin(new java.awt.Insets(10, 10, 10, 10));

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

        lblActi.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblActi.setForeground(new java.awt.Color(133, 207, 203));
        lblActi.setText("Activado:");

        lbAdmin.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbAdmin.setForeground(new java.awt.Color(133, 207, 203));
        lbAdmin.setText("Admin:");

        btnRegis.setBackground(new java.awt.Color(59, 44, 133));
        btnRegis.setForeground(new java.awt.Color(255, 255, 255));
        btnRegis.setText("Registrarse");
        btnRegis.setBorder(null);
        btnRegis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisActionPerformed(evt);
            }
        });

        btnAtras.setBackground(new java.awt.Color(255, 51, 51));
        btnAtras.setForeground(new java.awt.Color(255, 255, 255));
        btnAtras.setText("Atras");
        btnAtras.setBorder(null);
        btnAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasActionPerformed(evt);
            }
        });

        btnLimpiar.setBackground(new java.awt.Color(59, 44, 133));
        btnLimpiar.setForeground(new java.awt.Color(255, 255, 255));
        btnLimpiar.setText("Limpiar");
        btnLimpiar.setBorder(null);
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        pnlTitulo.setBackground(new java.awt.Color(59, 44, 133));

        lblTit.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lblTit.setForeground(new java.awt.Color(255, 255, 255));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("ADDUPDATE");

        javax.swing.GroupLayout pnlTituloLayout = new javax.swing.GroupLayout(pnlTitulo);
        pnlTitulo.setLayout(pnlTituloLayout);
        pnlTituloLayout.setHorizontalGroup(
            pnlTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTituloLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTit, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE))
        );
        pnlTituloLayout.setVerticalGroup(
            pnlTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTituloLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTit, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblError.setForeground(new java.awt.Color(255, 0, 0));
        lblError.setText("Ninguno de los campos puede estar vac�o");
        lblError.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblPass, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                            .addComponent(lblEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtEmail)
                                    .addComponent(txtNom, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(20, 20, 20))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 38, Short.MAX_VALUE)
                                .addComponent(lblError, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(73, 73, 73)
                                .addComponent(btnRegis, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAtras, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(20, 20, 20))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblActi, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                            .addComponent(lbAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chbAdmin)
                            .addComponent(chbActi))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnlTitulo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(83, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNom, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPass, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblActi, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chbActi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chbAdmin))
                .addGap(18, 18, 18)
                .addComponent(lblError)
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRegis, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAtras, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addComponent(pnlTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 256, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegisActionPerformed
        lblError.setText("");
        if (!UtilText.someTextEmpty(txt)) {
            //TODO -> HACER SEGUN QUIEN LA ABRA MODIFICAR | A�ADIR
            if (opc == Constantes.OPC_INSERTAR) {
                InsertUser();
            } else {
                modUser();
            }
        } else {
            lblError.setText("Alguno de los campos est� vac�o");
        }
    }//GEN-LAST:event_btnRegisActionPerformed
    private void InsertUser() {
        try {
            SealedObject so = UtilSec.encapsularObjeto(pubKAjena, Constantes.INS_USER);//le decimos que queremos insertar
            UtilMsj.enviarObject(servidor, so);
            //Insertar
            User u = new User(0, txtNom.getText(), txtEmail.getText(),
                    UtilSec.hashPass(txtPass.getText()), chbActi.isSelected(), chbAdmin.isSelected());
            so = UtilSec.encapsularObjeto(pubKAjena, u);//le mandamos el usuario
            UtilMsj.enviarObject(servidor, so);
            so = (SealedObject) UtilMsj.recibirObjeto(servidor);//recibe mensaje para evitar duplicados
            boolean exist = (Boolean) UtilSec.desencriptarObjeto(so, privK);
            if (exist) {
                lblError.setText("El email ya est� registrado");
            } else {
                uList.add(u);
                JOptionPane.showMessageDialog(this, "Usario A�adido");
                this.setVisible(false);
            }
        } catch (NoSuchAlgorithmException | InvalidKeyException | IOException | IllegalBlockSizeException | NoSuchPaddingException | ClassNotFoundException | BadPaddingException ex) {
            JOptionPane.showMessageDialog(this, "Usario A�adido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modUser() {
        try {
            SealedObject so = UtilSec.encapsularObjeto(pubKAjena, Constantes.MOD_USER);//le decimos que queremos insertar
            UtilMsj.enviarObject(servidor, so);
            //modificar
            int id = uList.get(pos).getId();
            User u = new User(id, txtNom.getText(), txtEmail.getText(),
                    UtilSec.hashPass(txtPass.getText()), chbActi.isSelected(), chbAdmin.isSelected());
            so = UtilSec.encapsularObjeto(pubKAjena, u);//le mandamos el usuario
            UtilMsj.enviarObject(servidor, so);
            so = (SealedObject) UtilMsj.recibirObjeto(servidor);//recibe mensaje para evitar duplicados
            boolean canUpdate = (Boolean) UtilSec.desencriptarObjeto(so, privK);
            if (!canUpdate) {
                lblError.setText("No se ha podido modificar");
            } else {
                JOptionPane.showMessageDialog(this, "Usario modificado");
                uList.set(pos, u);
                this.setVisible(false);
            }
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | IllegalBlockSizeException | ClassNotFoundException | BadPaddingException ex) {
            System.out.println(ex.getMessage());
        }
    }
    private void btnAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_btnAtrasActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        UtilText.cleanFields(txt);
        lblError.setText("");
    }//GEN-LAST:event_btnLimpiarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtras;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnRegis;
    private javax.swing.JCheckBox chbActi;
    private javax.swing.JCheckBox chbAdmin;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbAdmin;
    private javax.swing.JLabel lblActi;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblError;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPass;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel pnlTitulo;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNom;
    private javax.swing.JPasswordField txtPass;
    // End of variables declaration//GEN-END:variables

}
