/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frames.admin;

import java.awt.HeadlessException;
import java.io.IOException;
import java.net.*;
import java.security.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.*;
import javax.swing.*;
import javax.swing.table.*;
import model.User;
import util.*;

/**
 *
 * @author Ricardo
 */
public class dialogAdmin extends javax.swing.JDialog {

    private Socket servidor;
    private PrivateKey privK;
    private PublicKey pubK;
    private PublicKey pubKAjena;
    private DefaultTableModel modelo;
    private ArrayList<User> uList;

    /**
     * Creates new form dialogAdmin
     * @param parent
     * @param modal
     * @param servidor
     * @param privK
     * @param pubK
     * @param pubKAjena
     */
    public dialogAdmin(java.awt.Frame parent, boolean modal, Socket servidor, PrivateKey privK, PublicKey pubK, PublicKey pubKAjena) {
        super(parent, modal);
        initComponents();
        this.pubKAjena = pubKAjena;
        this.privK = privK;
        this.pubK = pubK;
        this.servidor = servidor;
        recibirArrayList();
        rellenarDatos();
    }
    
    private void rellenarDatos() {
        modelo = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            public Class<?> getColumnClass(int column) {
                if (column == 2 || column == 3) {
                    return Boolean.class;
                } else {
                    return String.class;
                }
            }
        };
        tblUsu.setModel(modelo);
        tblUsu.getTableHeader().setReorderingAllowed(false);
        tblUsu.setRowSelectionAllowed(true);
        tblUsu.setColumnSelectionAllowed(false);
        //se le a�aden al modelo las tablas
        modelo.addColumn("Nombre");
        modelo.addColumn("Email");
        modelo.addColumn("Activo");
        modelo.addColumn("Admin");
        System.out.println("Recibe arrayList " + uList.size());
        for (int i = 0; i < uList.size(); i++) {
            //se almacena en la tabla los datos de la fila del array
            modelo.addRow(new Object[]{uList.get(i).getName(), uList.get(i).getEmail(), (Boolean) uList.get(i).isActive(), uList.get(i).isAdmin()});
        }
    }

    private void recibirArrayList() {
        try {
            //envia el mensaje de que esta en la ventana admin y que quiere la arraylist
            SealedObject so = UtilSec.encapsularObjeto(pubKAjena, Constantes.ADMIN);
            UtilMsj.enviarObject(servidor, so);
            uList = (ArrayList<User>) UtilMsj.recibirObjetoCifrado(servidor, privK);//recibe la lista de usuarios
        } catch (IOException | ClassNotFoundException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
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

        jPanel1 = new javax.swing.JPanel();
        SPnl = new javax.swing.JScrollPane();
        tblUsu = new javax.swing.JTable();
        pnlTit = new javax.swing.JPanel();
        lblTitloh = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnActivar = new javax.swing.JButton();
        btnAdmin = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(44, 40, 40));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, java.awt.Color.lightGray, null));

        tblUsu.setForeground(new java.awt.Color(44, 40, 40));
        tblUsu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Nombre", "Email", "Activo", "Admin"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Boolean.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblUsu.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        SPnl.setViewportView(tblUsu);
        if (tblUsu.getColumnModel().getColumnCount() > 0) {
            tblUsu.getColumnModel().getColumn(0).setResizable(false);
            tblUsu.getColumnModel().getColumn(3).setResizable(false);
        }

        pnlTit.setBackground(new java.awt.Color(59, 44, 133));

        lblTitloh.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lblTitloh.setForeground(new java.awt.Color(255, 255, 255));
        lblTitloh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitloh.setText("Adminstrar usuarios");

        javax.swing.GroupLayout pnlTitLayout = new javax.swing.GroupLayout(pnlTit);
        pnlTit.setLayout(pnlTitLayout);
        pnlTitLayout.setHorizontalGroup(
            pnlTitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTitLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitloh, javax.swing.GroupLayout.DEFAULT_SIZE, 591, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlTitLayout.setVerticalGroup(
            pnlTitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTitLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitloh, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnAdd.setBackground(new java.awt.Color(59, 44, 133));
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("A�adir");
        btnAdd.setBorder(null);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(59, 44, 133));
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("Baja");
        btnDelete.setBorder(null);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(59, 44, 133));
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("Modificar");
        btnUpdate.setBorder(null);
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnCancel.setBackground(new java.awt.Color(255, 51, 51));
        btnCancel.setForeground(new java.awt.Color(255, 255, 255));
        btnCancel.setText("Cancelar");
        btnCancel.setBorder(null);
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnActivar.setBackground(new java.awt.Color(59, 44, 133));
        btnActivar.setForeground(new java.awt.Color(255, 255, 255));
        btnActivar.setText("Activar");
        btnActivar.setBorder(null);
        btnActivar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActivarActionPerformed(evt);
            }
        });

        btnAdmin.setBackground(new java.awt.Color(59, 44, 133));
        btnAdmin.setForeground(new java.awt.Color(255, 255, 255));
        btnAdmin.setText("Admin");
        btnAdmin.setBorder(null);
        btnAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdminActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnActivar, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6))
                    .addComponent(SPnl, javax.swing.GroupLayout.PREFERRED_SIZE, 557, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(pnlTit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SPnl, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnActivar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
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

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.setVisible(false);
        try {
            this.setVisible(false);
            SealedObject so = UtilSec.encapsularObjeto(pubKAjena, Constantes.CERRAR);//le mandamos el usuario
            UtilMsj.enviarObject(servidor, so);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | IllegalBlockSizeException ex) {
            Logger.getLogger(AddUpdateUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        AddUpdateUser adu = new AddUpdateUser(new javax.swing.JDialog(), true, servidor, privK, pubK, pubKAjena, Constantes.OPC_INSERTAR, uList, -1);
        rellenarDatos();
        adu.setVisible(true);
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (tblUsu.getSelectedRow() >= 0) {
            try {
                int opcion = JOptionPane.showConfirmDialog(null, "�Seguro "
                        + "que quiere dar de baja al usuario?", "Borrar", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (opcion == JOptionPane.YES_OPTION) {
                    //MANDAR DATOS AL SERVIDOR DE QUE QUIERE BORRAR
                    SealedObject so = UtilSec.encapsularObjeto(pubKAjena, Constantes.DEL_USER);//le mandamos el usuario
                    UtilMsj.enviarObject(servidor, so);
                    so = UtilSec.encapsularObjeto(pubKAjena, uList.get(tblUsu.getSelectedRow()).getId());//le mandamos el usuario
                    UtilMsj.enviarObject(servidor, so);
                    boolean canDelete = (Boolean) UtilMsj.recibirObjetoCifrado(servidor, privK);//recibe mensaje para ver si ha ido correcto
                    if (!canDelete) {
                        JOptionPane.showMessageDialog(this, "Ha ocurrido un erro al borrar", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Usario eliminado");
                        uList.remove(tblUsu.getSelectedRow());
                    }
                    rellenarDatos();
                }
            } catch (NullPointerException npe) {
                rellenarDatos();
            } catch (HeadlessException | IOException | InvalidKeyException | NoSuchAlgorithmException | IllegalBlockSizeException | NoSuchPaddingException | ClassNotFoundException | BadPaddingException ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debes seleccionar una fila");
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (tblUsu.getSelectedRow() >= 0) {
            AddUpdateUser adu = new AddUpdateUser(new javax.swing.JDialog(), true, servidor, privK, pubK, pubKAjena, Constantes.OPC_MODIFICAR, uList, tblUsu.getSelectedRow());
            adu.setVisible(true);
            rellenarDatos();
        } else {
            JOptionPane.showMessageDialog(this, "Debes seleccionar una fila");
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnActivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActivarActionPerformed
        if (tblUsu.getSelectedRow() >= 0) {
            if (uList.get(tblUsu.getSelectedRow()).isActive()) {
                JOptionPane.showMessageDialog(this, "Usuario ya activado");
            } else {
                try {
                    //MANDAR DATOS AL SERVIDOR DE QUE QUIERE ACTIVAR
                    SealedObject so = UtilSec.encapsularObjeto(pubKAjena, Constantes.ACT_USER);//le mandamos la opcion
                    UtilMsj.enviarObject(servidor, so);
                    so = UtilSec.encapsularObjeto(pubKAjena, uList.get(tblUsu.getSelectedRow()).getId());//le mandamos el id
                    UtilMsj.enviarObject(servidor, so);
                    boolean canActivate = (Boolean) UtilMsj.recibirObjetoCifrado(servidor, privK);//recibe mensaje para ver si ha ido correcto
                    if (!canActivate) {
                        JOptionPane.showMessageDialog(this, "Ha ocurrido un erro al activar", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Usario activado");
                        uList.get(tblUsu.getSelectedRow()).setActive(true);
                    }
                    rellenarDatos();
                } catch (NullPointerException npe) {
                    rellenarDatos();
                } catch (HeadlessException | IOException | InvalidKeyException | NoSuchAlgorithmException | IllegalBlockSizeException | NoSuchPaddingException | ClassNotFoundException | BadPaddingException ex) {
                    System.out.println("error: " + ex.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debes seleccionar una fila");
        }
    }//GEN-LAST:event_btnActivarActionPerformed

    private void btnAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdminActionPerformed
        if (tblUsu.getSelectedRow() >= 0) {
            try {
                boolean admin = uList.get(tblUsu.getSelectedRow()).isAdmin();
                //invertimos los valores
                if (admin) {
                    admin = false;
                } else {
                    admin = true;
                }
                //MANDAR DATOS AL SERVIDOR DE QUE QUIERE ACTIVAR
                SealedObject so = UtilSec.encapsularObjeto(pubKAjena, Constantes.ADMIN_USER);//le mandamos la opcion
                UtilMsj.enviarObject(servidor, so);
                so = UtilSec.encapsularObjeto(pubKAjena, uList.get(tblUsu.getSelectedRow()).getId());//le mandamos el id
                UtilMsj.enviarObject(servidor, so);
                so = UtilSec.encapsularObjeto(pubKAjena, admin);//le mandamos el id
                UtilMsj.enviarObject(servidor, so);
                so = (SealedObject) UtilMsj.recibirObjeto(servidor);//recibe mensaje para ver si ha ido correcto
                boolean canActivate = (Boolean) UtilSec.desencriptarObjeto(so, privK);
                if (!canActivate) {
                    JOptionPane.showMessageDialog(this, "Ha ocurrido un erro al Quitar/Poner a administrador", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (admin) {
                        JOptionPane.showMessageDialog(this, "Usario como administrador");
                    } else {
                        JOptionPane.showMessageDialog(this, "Usario arrebatado administrador");
                    }

                    uList.get(tblUsu.getSelectedRow()).setAdmin(admin);
                }
                rellenarDatos();
            } catch (NullPointerException npe) {
                rellenarDatos();
            } catch (HeadlessException | IOException | InvalidKeyException | NoSuchAlgorithmException | IllegalBlockSizeException | NoSuchPaddingException | ClassNotFoundException | BadPaddingException ex) {
                System.out.println("error: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debes seleccionar una fila");
        }
    }//GEN-LAST:event_btnAdminActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane SPnl;
    private javax.swing.JButton btnActivar;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAdmin;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblTitloh;
    private javax.swing.JPanel pnlTit;
    private javax.swing.JTable tblUsu;
    // End of variables declaration//GEN-END:variables
}
