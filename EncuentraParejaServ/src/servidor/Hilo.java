package servidor;

import bd.Mysql;
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
import javax.swing.table.DefaultTableModel;
import model.Preferencia;
import model.User;
import util.*;

/**
 * @author Ricardo
 */
public class Hilo extends Thread {

    private Mysql bd;
    private Socket cliente;
    private PrivateKey privK;
    private PublicKey pubK;
    private PublicKey pubKCAjena;

    Hilo(Mysql bd, Socket cliente) {
        this.bd = bd;
        this.cliente = cliente;
    }

    @Override
    public void run() {
        System.out.println("Recibiendo nuevo cliente");
        generarPrivPub();//genera las claves
        recMandPub();
        while (true) {
            try {
                SealedObject so = (SealedObject) UtilMsj.recibirObjeto(cliente);//recibe la opcion encapsulado
                int opc = (int) UtilSec.desencriptarObjeto(so, privK);
                if (opc == Constantes.LOGIN) {
                    System.out.println(Constantes.LOGIN);
                    logUser();
                } else {
                    System.out.println(Constantes.REGISTRAR);
                    regUser();
                }
            } catch (NullPointerException | IOException | ClassNotFoundException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
                try {
                    cliente.close();
                } catch (IOException ex) {
                }
            }
        }
    }

    private void logUser() {
        try {
            SealedObject so = (SealedObject) UtilMsj.recibirObjeto(cliente);//recibe el usuario encapsulado
            User u = (User) UtilSec.desencriptarObjeto(so, privK);
            u = bd.selectUser(u.getEmail(), u.getPass());
            if (u != null) {
                mandarUser(u);
                if (u.isActive()) {
                    ventanaPrincipal();
                }
            } else {
                so = UtilSec.encapsularObjeto(pubKCAjena, false);//no existe
            }
            //despues de comprobar envia el estado
            UtilMsj.enviarObject(cliente, so);//envia boolean 
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | ClassNotFoundException | IllegalBlockSizeException | BadPaddingException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void regUser() {
        try {
            //TODO -> mirar como firmar y cifrar lo dem�s
            //quiere registrarse
            SealedObject so = (SealedObject) UtilMsj.recibirObjeto(cliente);//recibe el objeto encapsulado
            User u = (User) UtilSec.desencriptarObjeto(so, privK);
            //inserta el usuario en la base de datos y devuelve si ha sido correcto o no
            boolean exist = bd.RegisUser(u.getName(), u.getEmail(), u.getPass());
            so = UtilSec.encapsularObjeto(pubKCAjena, exist);
            UtilMsj.enviarObject(cliente, so);//envia boolean
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | ClassNotFoundException | IllegalBlockSizeException | BadPaddingException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void mandarUser(User u) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, IllegalBlockSizeException {
        SealedObject so = UtilSec.encapsularObjeto(pubKCAjena, true);//existe en bd
        UtilMsj.enviarObject(cliente, so);
        so = UtilSec.encapsularObjeto(pubKCAjena, u);
        UtilMsj.enviarObject(cliente, so);
    }

    private void generarPrivPub() {
        KeyPair kp = UtilSec.generarclavePar();
        privK = kp.getPrivate();
        pubK = kp.getPublic();
    }

    private void recMandPub() {
        //recibe y envia las publicas
        pubKCAjena = (PublicKey) UtilMsj.recibirObjeto(cliente);//recibe la publica
        UtilMsj.enviarObject(cliente, pubK);//manda la publica
    }

    private void ventanaPrincipal() {
        //se queda esperando de forma infinita llamadas de la ventana principal
        boolean salir = false;
        while (!salir) {
            try {
                SealedObject so = (SealedObject) UtilMsj.recibirObjeto(cliente);//recibir� un mensaje y le dir� que ventana ha abierto
                String msj = (String) UtilSec.desencriptarObjeto(so, privK);
                switch (msj) {
                    case Constantes.SALIR:
                        System.out.println(msj);
                        salir = true;
                        break;
                    case Constantes.ADMIN:
                        System.out.println(msj);
                        admin();
                        break;
                    case Constantes.PRIMERA_VEZ:
                        System.out.println(msj);
                        prefPrimera();
                        break;
                    case Constantes.INS_PREF:
                        System.out.println(msj);
                        insertarPref();
                        break;
                    case Constantes.MOSTR_PREFS:
                        System.out.println(msj);
                        mostrarprefs();
                        break;
                    case Constantes.MANDA_MEGUSTA:
                        System.out.println(msj);
                        meGusta();
                        break;
                    case Constantes.LIST_AMIGOS:
                        System.out.println(msj);
                        listaAmigos();
                        break;
                    default:
                        throw new AssertionError();
                }
            } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | ClassNotFoundException | IllegalBlockSizeException | BadPaddingException ex) {
                System.out.println(ex.getMessage());
            }
        }
        try {
            cliente.close();
            System.out.println("Se cierra el canal");
        } catch (IOException e) {
        }

    }

    private void listaAmigos() {
        try {
            //recibimos el id del usuario
            SealedObject so = (SealedObject) UtilMsj.recibirObjeto(cliente);//recibe el id del usuario
            int id = (int) UtilSec.desencriptarObjeto(so, privK);
            ArrayList<User> uList = bd.listaAmigos(id);
            so = UtilSec.encapsularObjeto(pubKCAjena, uList);
            UtilMsj.enviarObject(cliente, so);//le mandamos la lista de amigos que tiene
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | ClassNotFoundException | IllegalBlockSizeException | BadPaddingException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void meGusta() {
        try {
            SealedObject so = (SealedObject) UtilMsj.recibirObjeto(cliente);//recibe el id del que le gusta
            int idAmigo = (int) UtilSec.desencriptarObjeto(so, privK);
            so = (SealedObject) UtilMsj.recibirObjeto(cliente);//recibe el id del usuario
            int idUser = (int) UtilSec.desencriptarObjeto(so, privK);
            if (bd.leGustas(idUser)) {
                bd.nosGustamos(idUser);//actualiza a true con lo que son amigos y le mandas un msj para decir se gustan
                so = UtilSec.encapsularObjeto(pubKCAjena, Constantes.RECIPROCO);
                UtilMsj.enviarObject(cliente, so);//le mandamos que se quieren ambos
            } else if (!bd.sonAmigos(idUser)) { // si no son amigos aun
                if (bd.yaMandoPeticion(idUser)) {
                    so = UtilSec.encapsularObjeto(pubKCAjena, Constantes.YAMANDO);
                    UtilMsj.enviarObject(cliente, so);//le mandamos que ha enviado su peticion
                } else {
                    //le manda una peticion
                    bd.meGusta(idUser, idAmigo);
                    so = UtilSec.encapsularObjeto(pubKCAjena, Constantes.MANDAPETI);
                    UtilMsj.enviarObject(cliente, so);//le mandamos que ha enviado su peticion
                }
            } else {
                so = UtilSec.encapsularObjeto(pubKCAjena, Constantes.YAMIGOS);
                UtilMsj.enviarObject(cliente, so);//le manda que ya eran amigos   
            }
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | ClassNotFoundException | IllegalBlockSizeException | BadPaddingException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void mostrarprefs() {
        try {
            SealedObject so = (SealedObject) UtilMsj.recibirObjeto(cliente);//recibe el usuario
            User us = (User) UtilSec.desencriptarObjeto(so, privK);
            Preferencia p = bd.selectPrefes(us.getId());
            if (p != null) {
                ArrayList<ArrayList<Object>> list = bd.mismasPref(us, p);
                so = UtilSec.encapsularObjeto(pubKCAjena, list);//le mandamo la lista de usuarios con prefs parecidas
                UtilMsj.enviarObject(cliente, so);
            }
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | ClassNotFoundException | IllegalBlockSizeException | BadPaddingException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void insertarPref() {
        try {
            ArrayList<String> rel = bd.relacion();
            ArrayList<String> tqhijos = bd.hijos();
            ArrayList<String> interes = bd.interes();
            SealedObject so = UtilSec.encapsularObjeto(pubKCAjena, rel);//le mandamos lista de relaciones
            UtilMsj.enviarObject(cliente, so);
            so = UtilSec.encapsularObjeto(pubKCAjena, tqhijos);//le mandamos lista de si quiere o no hijos
            UtilMsj.enviarObject(cliente, so);
            so = UtilSec.encapsularObjeto(pubKCAjena, interes);//le mandamos lista de interes
            UtilMsj.enviarObject(cliente, so);
            //una vez mandados los cmbbs espera a recibir la preferencia
            so = (SealedObject) UtilMsj.recibirObjeto(cliente);//recibir� un objeto preferencia
            Preferencia pref = (Preferencia) UtilSec.desencriptarObjeto(so, privK);
            boolean exists = bd.insertPrefs(pref);
            so = UtilSec.encapsularObjeto(pubKCAjena, exists);//le mandamos si existe
            UtilMsj.enviarObject(cliente, so);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | IllegalBlockSizeException | ClassNotFoundException | BadPaddingException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void prefPrimera() {
        try {
            SealedObject so = (SealedObject) UtilMsj.recibirObjeto(cliente);//recibir� un mensaje y le dira su id
            int id = (int) UtilSec.desencriptarObjeto(so, privK);
            boolean tienePrefs = bd.primeraVez(id);
            so = UtilSec.encapsularObjeto(pubKCAjena, tienePrefs);//le mandamos si es la primera vez o no
            UtilMsj.enviarObject(cliente, so);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | ClassNotFoundException | IllegalBlockSizeException | BadPaddingException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void admin() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, IllegalBlockSizeException {
        try {
            SealedObject so;//encapsulamos el arrayList
            ArrayList<User> listu = bd.listaUserSinPsw();
            so = UtilSec.encapsularObjeto(pubKCAjena, listu);
            UtilMsj.enviarObject(cliente, so);//enviamos la arrayList
            System.out.println("Envia arraylist");
            // TODO -> se queda esperando a recibir un mensaje de lo que quiere hacer
            //"ALTA","BAJA","MODIFICAR","CERRAR"
            Boolean cerrar = false;
            while (!cerrar) {
                so = (SealedObject) UtilMsj.recibirObjeto(cliente);//recibir� un mensaje y le dir� que quiere hacer
                String msj = (String) UtilSec.desencriptarObjeto(so, privK);
                switch (msj) {
                    case Constantes.MOD_USER:
                        System.out.println(msj);
                        modUser();
                        break;
                    case Constantes.DEL_USER:
                        System.out.println(msj);
                        delUser();
                        break;
                    case Constantes.INS_USER:
                        System.out.println(msj);
                        insertUser();
                        break;
                    case Constantes.ADMIN_USER:
                        System.out.println(msj);
                        actDesAdminUser();
                        break;
                    case Constantes.ACT_USER:
                        System.out.println(msj);
                        activUser();
                        break;
                    case Constantes.CERRAR:
                        System.out.println("Cierra ventana");
                        cerrar = true;
                        break;
                    default:
                }
            }
        } catch (ClassNotFoundException | BadPaddingException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void actDesAdminUser() {
        try {
            SealedObject so = (SealedObject) UtilMsj.recibirObjeto(cliente);//recibir� un mensaje y le dira su id
            int id = (int) UtilSec.desencriptarObjeto(so, privK);
            so = (SealedObject) UtilMsj.recibirObjeto(cliente);//recibir� un boolean de si quiere quitar/poner admin
            boolean admin = (boolean) UtilSec.desencriptarObjeto(so, privK);
            boolean canModAdmin = bd.actDesAdminUser(id, admin);
            so = UtilSec.encapsularObjeto(pubKCAjena, canModAdmin);
            UtilMsj.enviarObject(cliente, so);//envia boolean
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | ClassNotFoundException | IllegalBlockSizeException | BadPaddingException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void activUser() {
        try {
            SealedObject so = (SealedObject) UtilMsj.recibirObjeto(cliente);//recibir� un id para activar
            int id = (int) UtilSec.desencriptarObjeto(so, privK);
            boolean canActivate = bd.activateUser(id);
            so = UtilSec.encapsularObjeto(pubKCAjena, canActivate);
            UtilMsj.enviarObject(cliente, so);//envia boolean
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | ClassNotFoundException | IllegalBlockSizeException | BadPaddingException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void delUser() {
        try {
            SealedObject so = (SealedObject) UtilMsj.recibirObjeto(cliente);//recibir� un mensaje y le dir� que quiere hacer
            int id = (int) UtilSec.desencriptarObjeto(so, privK);
            boolean canDelete = bd.deleteUser(id);
            so = UtilSec.encapsularObjeto(pubKCAjena, canDelete);
            UtilMsj.enviarObject(cliente, so);//envia boolean
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | ClassNotFoundException | IllegalBlockSizeException | BadPaddingException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void modUser() {
        try {
            SealedObject so = (SealedObject) UtilMsj.recibirObjeto(cliente);//recibir� un mensaje y le dir� que quiere hacer
            User user = (User) UtilSec.desencriptarObjeto(so, privK);
            boolean exist = bd.updateUser(user);
            so = UtilSec.encapsularObjeto(pubKCAjena, exist);
            UtilMsj.enviarObject(cliente, so);//envia boolean
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | ClassNotFoundException | IllegalBlockSizeException | BadPaddingException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void insertUser() {
        try {
            SealedObject so = (SealedObject) UtilMsj.recibirObjeto(cliente);//recibir� un mensaje y le dir� que quiere hacer
            User user = (User) UtilSec.desencriptarObjeto(so, privK);
            boolean exist = bd.AniadirUser(user);
            so = UtilSec.encapsularObjeto(pubKCAjena, exist);
            UtilMsj.enviarObject(cliente, so);//envia boolean
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | ClassNotFoundException | IllegalBlockSizeException | BadPaddingException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
