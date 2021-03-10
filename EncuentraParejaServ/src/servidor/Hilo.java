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
                int opc = (int) UtilMsj.recibirObjetoCifrado(cliente, privK);//recibe la opcion LOG / REG
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
            SealedObject so = null;
            User u = (User) UtilMsj.recibirObjetoCifrado(cliente, privK);
            u = bd.selectUser(u.getEmail(), u.getPass());
            if (u != null) {
                mandarUser(u);
                if (u.isActive()) {
                    ventanaPrincipal();
                }
            } else {
                so = UtilSec.encapsularObjeto(pubKCAjena, false);//no existe
                UtilMsj.enviarObject(cliente, so);
            }
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

    private void regUser() {
        try {
            //quiere registrarse
            User u = (User) UtilMsj.recibirObjetoCifrado(cliente, privK);
            //inserta el usuario en la base de datos y devuelve si ha sido correcto o no
            boolean exist = bd.RegisUser(u.getName(), u.getEmail(), u.getPass());
            SealedObject so = UtilSec.encapsularObjeto(pubKCAjena, exist);
            UtilMsj.enviarObject(cliente, so);//envia boolean
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | ClassNotFoundException | IllegalBlockSizeException | BadPaddingException ex) {
            System.out.println(ex.getMessage());
        }
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
                String msj = (String) UtilMsj.recibirObjetoCifrado(cliente, privK);
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
                    case Constantes.EDIT_PREFE:
                        System.out.println(msj);
                        editPref();
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

    private void editPref() {
        try {
            int id = (int) UtilMsj.recibirObjetoCifrado(cliente, privK);//recibe el id
            Preferencia pref = bd.selectPrefes(id);
            SealedObject so = UtilSec.encapsularObjeto(pubKCAjena, pref);//se envian las prefs
            UtilMsj.enviarObject(cliente, so);
            mandarCmbb();
            String msj = (String) UtilMsj.recibirObjetoCifrado(cliente, privK);
            switch (msj) {
                case Constantes.CERRAR:
                    break;
                case Constantes.EDIT_PREFE:
                    pref = (Preferencia) UtilMsj.recibirObjetoCifrado(cliente, privK);
                    boolean error = bd.UpdatePrefs(pref);
                    so = UtilSec.encapsularObjeto(pubKCAjena, error);//se envia si ha dado error
                    UtilMsj.enviarObject(cliente, so);
                    break;
                default:

            }

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | ClassNotFoundException | IllegalBlockSizeException | BadPaddingException ex) {
            System.out.println(ex.getMessage());
        }

    }

    private void listaAmigos() {
        try {
            //recibimos el id del usuario
            int id = (int) UtilMsj.recibirObjetoCifrado(cliente, privK);//recibe el id del usuario
            ArrayList<User> uList = bd.listaAmigos(id);
            SealedObject so = UtilSec.encapsularObjeto(pubKCAjena, uList);
            UtilMsj.enviarObject(cliente, so);//le mandamos la lista de amigos que tiene
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | ClassNotFoundException | IllegalBlockSizeException | BadPaddingException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void meGusta() {
        try {
            SealedObject so;
            int idAmigo = (int) UtilMsj.recibirObjetoCifrado(cliente, privK);//recibe el id del que le gusta
            int idUser = (int) UtilMsj.recibirObjetoCifrado(cliente, privK);//recibe el id del usuario
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
        SealedObject so;
        try {
            User us = (User) UtilMsj.recibirObjetoCifrado(cliente, privK);//recibe el usuario
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

    private void mandarCmbb() {
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
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | IllegalBlockSizeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void insertarPref() {
        try {
            mandarCmbb();
            //una vez mandados los cmbbs espera a recibir la preferencia
            Preferencia pref = (Preferencia) UtilMsj.recibirObjetoCifrado(cliente, privK);//recibirá un objeto preferencia
            boolean exists = bd.insertPrefs(pref);
            SealedObject so = UtilSec.encapsularObjeto(pubKCAjena, exists);//le mandamos si existe
            UtilMsj.enviarObject(cliente, so);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | IllegalBlockSizeException | ClassNotFoundException | BadPaddingException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void prefPrimera() {
        SealedObject so;
        try {
            int id = (int) UtilMsj.recibirObjetoCifrado(cliente, privK);//recibirá un mensaje y le dira su id
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
            //"ALTA","BAJA","MODIFICAR","CERRAR"
            Boolean cerrar = false;
            while (!cerrar) {
                String msj = (String) UtilMsj.recibirObjetoCifrado(cliente, privK);//recibirá un mensaje y le dirá que quiere hacer
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
                        System.out.println(msj);
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
        SealedObject so;
        try {
            int id = (int) UtilMsj.recibirObjetoCifrado(cliente, privK);//recibirá un mensaje y le dira su id
            boolean admin = (boolean) UtilMsj.recibirObjetoCifrado(cliente, privK);//recibirá un boolean de si quiere quitar/poner admin
            boolean canModAdmin = bd.actDesAdminUser(id, admin);
            so = UtilSec.encapsularObjeto(pubKCAjena, canModAdmin);
            UtilMsj.enviarObject(cliente, so);//envia boolean
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | ClassNotFoundException | IllegalBlockSizeException | BadPaddingException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void activUser() {
        try {
            int id = (int) UtilMsj.recibirObjetoCifrado(cliente, privK);//recibirá un id para activar
            boolean canActivate = bd.activateUser(id);
            SealedObject so = UtilSec.encapsularObjeto(pubKCAjena, canActivate);
            UtilMsj.enviarObject(cliente, so);//envia boolean
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | ClassNotFoundException | IllegalBlockSizeException | BadPaddingException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void delUser() {
        try {
            int id = (int) UtilMsj.recibirObjetoCifrado(cliente, privK);//recibirá un mensaje y le dirá que quiere hacer
            boolean canDelete = bd.deleteUser(id);
            SealedObject so = UtilSec.encapsularObjeto(pubKCAjena, canDelete);
            UtilMsj.enviarObject(cliente, so);//envia boolean
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | ClassNotFoundException | IllegalBlockSizeException | BadPaddingException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void modUser() {
        try {
            User user = (User) UtilMsj.recibirObjetoCifrado(cliente, privK);//recibirá un mensaje y le dirá que quiere hacer
            boolean exist = bd.updateUser(user);
            SealedObject so = UtilSec.encapsularObjeto(pubKCAjena, exist);
            UtilMsj.enviarObject(cliente, so);//envia boolean
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | ClassNotFoundException | IllegalBlockSizeException | BadPaddingException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void insertUser() {
        try {
            User user = (User) UtilMsj.recibirObjetoCifrado(cliente, privK);//recibirá un mensaje y le dirá que quiere hacer
            boolean exist = bd.AniadirUser(user);
            SealedObject so = UtilSec.encapsularObjeto(pubKCAjena, exist);
            UtilMsj.enviarObject(cliente, so);//envia boolean
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | ClassNotFoundException | IllegalBlockSizeException | BadPaddingException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
