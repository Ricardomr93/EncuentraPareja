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
        boolean valide = false;
        User u;
        generarPrivPub();//genera las claves
        recMandPub();
        while (true) {
            try {
                int opc = UtilMsj.recibirInt(cliente);//recibe la opcion (1)login / (2)registrarse
                if (opc == Constantes.LOGIN) {
                    logUser();
                } else {
                    regUser();
                }
            } catch (Exception e) {
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
                ventanaPrincipal();
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
            //TODO -> mirar como firmar y cifrar lo demás
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
        while (true) {
            try {
                SealedObject so = (SealedObject) UtilMsj.recibirObjeto(cliente);//recibirá un mensaje y le dirá que ventana ha abierto
                String msj = (String) UtilSec.desencriptarObjeto(so, privK);
                switch (msj) {
                    case Constantes.ADMIN:
                        System.out.println(msj);
                        admin();
                        break;
                    default:
                        throw new AssertionError();
                }
            } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | ClassNotFoundException | IllegalBlockSizeException | BadPaddingException ex) {
                System.out.println(ex.getMessage());
            }
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
                so = (SealedObject) UtilMsj.recibirObjeto(cliente);//recibirá un mensaje y le dirá que quiere hacer
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
            SealedObject so = (SealedObject) UtilMsj.recibirObjeto(cliente);//recibirá un mensaje y le dira su id
            int id = (int) UtilSec.desencriptarObjeto(so, privK);
            so = (SealedObject) UtilMsj.recibirObjeto(cliente);//recibirá un boolean de si quiere quitar/poner admin
            boolean admin = (boolean) UtilSec.desencriptarObjeto(so, privK);
            boolean canModAdmin = bd.actDesAdminUser(id,admin);
            so = UtilSec.encapsularObjeto(pubKCAjena, canModAdmin);
            UtilMsj.enviarObject(cliente, so);//envia boolean
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | ClassNotFoundException | IllegalBlockSizeException | BadPaddingException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void activUser() {
        try {
            SealedObject so = (SealedObject) UtilMsj.recibirObjeto(cliente);//recibirá un id para activar
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
            SealedObject so = (SealedObject) UtilMsj.recibirObjeto(cliente);//recibirá un mensaje y le dirá que quiere hacer
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
            SealedObject so = (SealedObject) UtilMsj.recibirObjeto(cliente);//recibirá un mensaje y le dirá que quiere hacer
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
            SealedObject so = (SealedObject) UtilMsj.recibirObjeto(cliente);//recibirá un mensaje y le dirá que quiere hacer
            User user = (User) UtilSec.desencriptarObjeto(so, privK);
            boolean exist = bd.AniadirUser(user);
            so = UtilSec.encapsularObjeto(pubKCAjena, exist);
            UtilMsj.enviarObject(cliente, so);//envia boolean
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | ClassNotFoundException | IllegalBlockSizeException | BadPaddingException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
