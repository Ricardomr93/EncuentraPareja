package servidor;

import bd.Mysql;
import java.net.*;
import java.security.*;
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
            //recibe y envia las publicas
            pubKClient = (PublicKey) UtilMsj.recibirObjeto(cliente);//recibe la publica
            UtilMsj.enviarObject(cliente, pubK);//manda la publica
            while(!cliente.isClosed()){
                int opc = UtilMsj.recibirInt(cliente);//recibe la opcion (1)login / (2)registrarse
                if (opc == 1) {
                }else{
                    //TODO -> mirar como firmar y cifrar lo demás
                    //quiere registrarse
                    SealedObject so = (SealedObject) UtilMsj.recibirObjeto(cliente);//recibe el objeto encapsulado
                    u = (User)UtilSec.desencapsularUser(privK, so);
                    bd.insertUser(u.getName(), u.getEmail(), u.getPass());//inserta el usuario en la base de datos
                }
            }
        } catch (Exception e) {
        }
    }
    public void generarPrivPub() {
        KeyPair kp = UtilSec.generarclavePar();
        privK = kp.getPrivate();
        pubK = kp.getPublic();
    }

}
