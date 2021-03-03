package servidor;

import bd.Mysql;
import java.io.IOException;
import java.net.*;
import java.security.*;
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
    private PublicKey pubKClient;

    Hilo(Mysql bd, Socket cliente) {
        this.bd = bd;
        this.cliente = cliente;
    }

    @Override
    public void run() {
        try {
            System.out.println("Recibiendo nuevo cliente");
            boolean valide = false;
            User u;
            generarPrivPub();//genera las claves
            recMandPub();
            while (cliente.isBound()) {
                int opc = UtilMsj.recibirInt(cliente);//recibe la opcion (1)login / (2)registrarse
                if (opc == 1) {
                    SealedObject so = (SealedObject) UtilMsj.recibirObjeto(cliente);//recibe el usuario encapsulado
                    u = (User) UtilSec.desencriptarObjeto(so, privK);
                    u = bd.selectUser(u.getEmail(), u.getPass());
                    if (u != null) {
                        so = UtilSec.encapsularObjeto(pubKClient, true);//existe en bd
                    } else {
                        so = UtilSec.encapsularObjeto(pubKClient, false);//no existe
                    }
                    UtilMsj.enviarObject(cliente, so);//envia boolean
                } else {
                    //TODO -> mirar como firmar y cifrar lo demás
                    //quiere registrarse
                    SealedObject so = (SealedObject) UtilMsj.recibirObjeto(cliente);//recibe el objeto encapsulado
                    u = (User) UtilSec.desencriptarObjeto(so, privK);
                    //inserta el usuario en la base de datos y devuelve si ha sido correcto o no
                    boolean exist = bd.insertUser(u.getName(), u.getEmail(), u.getPass());
                    so = UtilSec.encapsularObjeto(pubKClient, exist);
                    UtilMsj.enviarObject(cliente, so);//envia boolean
                }
            }
        } catch (IOException | ClassNotFoundException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
        } catch (NullPointerException nul) {

        }
    }

    public void generarPrivPub() {
        KeyPair kp = UtilSec.generarclavePar();
        privK = kp.getPrivate();
        pubK = kp.getPublic();
    }

    public void recMandPub() {
        //recibe y envia las publicas
        pubKClient = (PublicKey) UtilMsj.recibirObjeto(cliente);//recibe la publica
        UtilMsj.enviarObject(cliente, pubK);//manda la publica
    }

}
