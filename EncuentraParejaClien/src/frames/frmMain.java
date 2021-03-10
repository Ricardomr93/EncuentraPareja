package frames;

import frames.admin.dialogAdmin;
import frames.amigos.dialogListAmigos;
import java.awt.Color;
import java.io.IOException;
import java.net.*;
import java.security.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Preferencia;
import model.User;
import util.Constantes;
import util.Render;
import util.UtilMsj;
import util.UtilSec;

/**
 *
 * @author Ricardo
 */
public class frmMain extends javax.swing.JFrame {

    private Socket servidor;
    private PrivateKey privK;
    private PublicKey pubK;
    private PublicKey pubKAjena;
    private User user;
    private DefaultTableModel modelo;
    private ArrayList<User> uList;
    private ArrayList<Preferencia> pList;

    /**
     * Creates new form frmMain
     *
     * @param servidor
     * @param privK
     * @param pubK
     * @param pubKAjena
     * @param user
     */
    public frmMain(Socket servidor, PrivateKey privK, PublicKey pubK, PublicKey pubKAjena, User user) {
        initComponents();
        this.pubKAjena = pubKAjena;
        this.privK = privK;
        this.pubK = pubK;
        this.servidor = servidor;
        this.user = user;
        isAdmin();
        primeraVez();
        imgApp.setIcon(new ImageIcon("src/images/Enpareja.png"));
        imgEnviar.setIcon(new ImageIcon("src/images/Send.png"));
        imgAmigo.setIcon(new ImageIcon("src/images/Love.png"));
    }

    private void rellenarDatos() {
        tabPref.setDefaultRenderer(Object.class, new Render());
        JButton btn = new JButton("");
        btn.setIcon(new ImageIcon("src/images/heart.png"));
        modelo = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            public Class<?> getColumnClass(int column) {
                return String.class;
            }
        };

        //se le añaden al modelo las tablas
        modelo.addColumn("Nombre");
        modelo.addColumn("Relacion");
        modelo.addColumn("Artistico");
        modelo.addColumn("Deportivo");
        modelo.addColumn("Politico");
        modelo.addColumn("Hijos");
        modelo.addColumn("Intereses");
        modelo.addColumn(" ");
        Object[] obj = new Object[8];
        try {
            for (int i = 0; i < uList.size(); i++) {
                obj[0] = uList.get(i).getName();
                obj[1] = pList.get(i).getRelacion();
                obj[2] = pList.get(i).isArtisticos();
                obj[3] = pList.get(i).getDeportivos();
                obj[4] = pList.get(i).getPoliticos();
                obj[5] = pList.get(i).getTqhijos();
                obj[6] = pList.get(i).getInteres();
                obj[7] = btn;
                //se almacena en la tabla los datos de la fila del array
                modelo.addRow(obj);
            }
            tabPref.setModel(modelo);
            tabPref.setRowHeight(30);
            tabPref.getTableHeader().setReorderingAllowed(false);
            tabPref.setRowSelectionAllowed(true);
            tabPref.setColumnSelectionAllowed(false);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void primeraVez() {
        try {
            //comprueba si es la primera vez que entra para pedir las preferencias
            int id = user.getId();
            SealedObject so = UtilSec.encapsularObjeto(pubKAjena, Constantes.PRIMERA_VEZ);//le mandamos la opcion
            UtilMsj.enviarObject(servidor, so);
            so = UtilSec.encapsularObjeto(pubKAjena, id);//le mandamos el id para que compruebe si tiene prefs
            UtilMsj.enviarObject(servidor, so);
            so = (SealedObject) UtilMsj.recibirObjeto(servidor);//recibe mensaje para ver si ha ido correcto
            boolean tienePrefs = (Boolean) UtilSec.desencriptarObjeto(so, privK);
            if (!tienePrefs) {
                dialogPreferencias dpref = new dialogPreferencias(this, true, id, servidor, privK, pubK, pubKAjena);
                dpref.setVisible(true);
            } else {
                mostrarPrefs();
                rellenarDatos();
            }
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | IllegalBlockSizeException | ClassNotFoundException | BadPaddingException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void mostrarPrefs() {
        try {
            uList = new ArrayList<>();
            pList = new ArrayList<>();
            SealedObject so = UtilSec.encapsularObjeto(pubKAjena, Constantes.MOSTR_PREFS);//le mandamos la opcion
            UtilMsj.enviarObject(servidor, so);
            so = UtilSec.encapsularObjeto(pubKAjena, user);//le mandamos el usuario
            UtilMsj.enviarObject(servidor, so);
            so = (SealedObject) UtilMsj.recibirObjeto(servidor);//recibe una lista de listas
            ArrayList<ArrayList<Object>> list = (ArrayList<ArrayList<Object>>) UtilSec.desencriptarObjeto(so, privK);
            //separamos las listas de una con usuarios y otra con las preferencias
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < list.get(i).size(); j++) {
                    if (i == 0) {
                        uList.add((User) list.get(i).get(j));
                    } else if (i == 1) {
                        pList.add((Preferencia) list.get(i).get(j));
                    }
                }
            }
            if (uList.isEmpty()) {

            }
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | IllegalBlockSizeException | ClassNotFoundException | BadPaddingException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void isAdmin() {
        if (!user.isAdmin()) {
            mnuAdmin.setVisible(false);
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

        jOptionPane1 = new javax.swing.JOptionPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jPanel1 = new javax.swing.JPanel();
        pnlDcha = new javax.swing.JPanel();
        pnlEnviar = new javax.swing.JPanel();
        imgEnviar = new javax.swing.JLabel();
        btnEnviar = new javax.swing.JButton();
        pnlAmigos = new javax.swing.JPanel();
        imgAmigo = new javax.swing.JLabel();
        btnListAmig = new javax.swing.JButton();
        imgApp = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabPref = new javax.swing.JTable();
        mnuPrinci = new javax.swing.JMenuBar();
        mnuAdmin = new javax.swing.JMenu();
        menuItemAdm = new javax.swing.JMenuItem();
        mnuEdit = new javax.swing.JMenu();
        mnuPerfil = new javax.swing.JMenuItem();
        mnuPref = new javax.swing.JMenuItem();
        mnuSesion = new javax.swing.JMenu();
        mnuExit = new javax.swing.JMenuItem();

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        pnlDcha.setBackground(new java.awt.Color(255, 255, 255));

        pnlEnviar.setBackground(new java.awt.Color(44, 40, 40));

        imgEnviar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        btnEnviar.setBackground(new java.awt.Color(59, 44, 133));
        btnEnviar.setForeground(new java.awt.Color(255, 255, 255));
        btnEnviar.setText("Mensajes");
        btnEnviar.setBorder(null);
        btnEnviar.setBorderPainted(false);

        javax.swing.GroupLayout pnlEnviarLayout = new javax.swing.GroupLayout(pnlEnviar);
        pnlEnviar.setLayout(pnlEnviarLayout);
        pnlEnviarLayout.setHorizontalGroup(
            pnlEnviarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEnviarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imgEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlEnviarLayout.setVerticalGroup(
            pnlEnviarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEnviarLayout.createSequentialGroup()
                .addComponent(imgEnviar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(pnlEnviarLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(btnEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        pnlAmigos.setBackground(new java.awt.Color(44, 40, 40));

        imgAmigo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        btnListAmig.setBackground(new java.awt.Color(59, 44, 133));
        btnListAmig.setForeground(new java.awt.Color(255, 255, 255));
        btnListAmig.setText("Lista amigos");
        btnListAmig.setBorder(null);
        btnListAmig.setBorderPainted(false);
        btnListAmig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListAmigActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlAmigosLayout = new javax.swing.GroupLayout(pnlAmigos);
        pnlAmigos.setLayout(pnlAmigosLayout);
        pnlAmigosLayout.setHorizontalGroup(
            pnlAmigosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAmigosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imgAmigo, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnListAmig, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlAmigosLayout.setVerticalGroup(
            pnlAmigosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAmigosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imgAmigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAmigosLayout.createSequentialGroup()
                .addContainerGap(42, Short.MAX_VALUE)
                .addComponent(btnListAmig, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );

        imgApp.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout pnlDchaLayout = new javax.swing.GroupLayout(pnlDcha);
        pnlDcha.setLayout(pnlDchaLayout);
        pnlDchaLayout.setHorizontalGroup(
            pnlDchaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDchaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDchaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlAmigos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlEnviar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(imgApp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlDchaLayout.setVerticalGroup(
            pnlDchaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDchaLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(imgApp, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(pnlEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlAmigos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(44, 40, 40));

        tabPref.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Nombre", "Relacion", "Artistico", "Deportivo", "Politico", "Hijos", "Interes", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabPref.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabPrefMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabPref);
        if (tabPref.getColumnModel().getColumnCount() > 0) {
            tabPref.getColumnModel().getColumn(0).setResizable(false);
            tabPref.getColumnModel().getColumn(1).setResizable(false);
            tabPref.getColumnModel().getColumn(2).setResizable(false);
            tabPref.getColumnModel().getColumn(3).setResizable(false);
            tabPref.getColumnModel().getColumn(4).setResizable(false);
            tabPref.getColumnModel().getColumn(5).setResizable(false);
            tabPref.getColumnModel().getColumn(6).setResizable(false);
            tabPref.getColumnModel().getColumn(7).setResizable(false);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 618, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlDcha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlDcha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        mnuPrinci.setBackground(new java.awt.Color(59, 44, 133));

        mnuAdmin.setBackground(new java.awt.Color(59, 44, 133));
        mnuAdmin.setText("Administrador");
        mnuAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAdminActionPerformed(evt);
            }
        });

        menuItemAdm.setText("Administar");
        menuItemAdm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemAdmActionPerformed(evt);
            }
        });
        mnuAdmin.add(menuItemAdm);

        mnuPrinci.add(mnuAdmin);

        mnuEdit.setBackground(new java.awt.Color(59, 44, 133));
        mnuEdit.setText("Editar");

        mnuPerfil.setText("Perfil");
        mnuEdit.add(mnuPerfil);

        mnuPref.setText("Preferencias");
        mnuEdit.add(mnuPref);

        mnuPrinci.add(mnuEdit);

        mnuSesion.setText("Sesion");

        mnuExit.setText("Salir");
        mnuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuExitActionPerformed(evt);
            }
        });
        mnuSesion.add(mnuExit);

        mnuPrinci.add(mnuSesion);

        setJMenuBar(mnuPrinci);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void mnuAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAdminActionPerformed

    }//GEN-LAST:event_mnuAdminActionPerformed

    private void menuItemAdmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemAdmActionPerformed
        dialogAdmin da = new dialogAdmin(this, true, servidor, privK, pubK, pubKAjena);
        da.setVisible(true);
    }//GEN-LAST:event_menuItemAdmActionPerformed

    private void mnuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuExitActionPerformed
        try {
            SealedObject so = UtilSec.encapsularObjeto(pubKAjena, Constantes.SALIR);//le mandamos la opcion
            UtilMsj.enviarObject(servidor, so);
            System.exit(0);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | IllegalBlockSizeException ex) {
            System.out.println(ex.getMessage());
        }
    }//GEN-LAST:event_mnuExitActionPerformed

    private void tabPrefMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabPrefMouseClicked
        int c = tabPref.getColumnModel().getColumnIndexAtX(evt.getX());
        int f = evt.getY() / tabPref.getRowHeight();

        if (f < tabPref.getRowCount() && f >= 0 && c < tabPref.getColumnCount() && c >= 0) {
            Object value = tabPref.getValueAt(f, c);
            if (value instanceof JButton) {
                ((JButton) value).doClick();
                JButton but = (JButton) value;
                int yes = JOptionPane.showConfirmDialog(this, "Te gusta esta persona?", "", JOptionPane.YES_NO_OPTION);
                if (JOptionPane.OK_OPTION == yes) {
                    meGusta(f);
                }
            }
        }
    }//GEN-LAST:event_tabPrefMouseClicked

    private void btnListAmigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListAmigActionPerformed
        dialogListAmigos dla = new dialogListAmigos(this, true, servidor, privK, pubK, pubKAjena, user);
        dla.setVisible(true);
    }//GEN-LAST:event_btnListAmigActionPerformed
    private void meGusta(int f) {
        try {
            SealedObject so = UtilSec.encapsularObjeto(pubKAjena, Constantes.MANDA_MEGUSTA);//le mandamos la opcion
            UtilMsj.enviarObject(servidor, so);
            int idAmigo = uList.get(f).getId();//cogemos el id de la fila que selecciona
            so = UtilSec.encapsularObjeto(pubKAjena, idAmigo);//le mandamos el ID del amigo
            UtilMsj.enviarObject(servidor, so);
            so = UtilSec.encapsularObjeto(pubKAjena, user.getId());//le mandamos el ID del usuario
            UtilMsj.enviarObject(servidor, so);
            //recibirá un mensaje de feedback
            so = (SealedObject) UtilMsj.recibirObjeto(servidor);//recibe una lista de listas
            String respuesta = (String) UtilSec.desencriptarObjeto(so, privK);
            String msj = "";
            switch (respuesta) {
                case Constantes.YAMIGOS:
                    msj = "Ya erais amigos";
                    break;
                case Constantes.MANDAPETI:
                    msj = "Me gusta enviado";
                    break;
                case Constantes.RECIPROCO:
                    msj = "Ahora os gustais ambos";
                    break;
                case Constantes.YAMANDO:
                    msj = "Ya mandaste una petición";
                    break;
                default:
            }
            JOptionPane.showMessageDialog(this, msj);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | IllegalBlockSizeException | ClassNotFoundException | BadPaddingException ex) {
            System.out.println(ex.getMessage());
        }

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEnviar;
    private javax.swing.JButton btnListAmig;
    private javax.swing.JLabel imgAmigo;
    private javax.swing.JLabel imgApp;
    private javax.swing.JLabel imgEnviar;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JOptionPane jOptionPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuItem menuItemAdm;
    private javax.swing.JMenu mnuAdmin;
    private javax.swing.JMenu mnuEdit;
    private javax.swing.JMenuItem mnuExit;
    private javax.swing.JMenuItem mnuPerfil;
    private javax.swing.JMenuItem mnuPref;
    private javax.swing.JMenuBar mnuPrinci;
    private javax.swing.JMenu mnuSesion;
    private javax.swing.JPanel pnlAmigos;
    private javax.swing.JPanel pnlDcha;
    private javax.swing.JPanel pnlEnviar;
    private javax.swing.JTable tabPref;
    // End of variables declaration//GEN-END:variables

}
