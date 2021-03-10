package util;

import java.io.*;
import java.net.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;

/**
 * @author Ricardo
 */
public class UtilMsj {

    public static void enviarObject(Socket receptor, Object objeto) {
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(receptor.getOutputStream());
            oos.writeObject(objeto);
        } catch (IOException ex) {
        }
    }
    public static void enviarObjetoCifrado(Socket s, PublicKey pubk, Object o) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, IllegalBlockSizeException {
        SealedObject so = UtilSec.encapsularObjeto(pubk, o);
        UtilMsj.enviarObject(s, so);//envia boolean
    }

    public static Object recibirObjetoCifrado(Socket s, PrivateKey pk) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, ClassNotFoundException, IllegalBlockSizeException, BadPaddingException {
        SealedObject so = (SealedObject) UtilMsj.recibirObjeto(s);//recibe el usuario encapsulado
        return UtilSec.desencriptarObjeto(so, pk);
    }

    public static Object recibirObjeto(Socket receptor) {

        ObjectInputStream ois;
        Object objeto = null;
        try {
            ois = new ObjectInputStream(receptor.getInputStream());
            objeto = ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
        }
        return objeto;
    }

    public static void enviarInt(Socket receptor, int opcion) {

        DataOutputStream dos;
        try {
            dos = new DataOutputStream(receptor.getOutputStream());
            dos.writeInt(opcion);
        } catch (IOException ex) {
        }
    }

    public static int recibirInt(Socket receptor) {

        int opcion = 0;
        try {
            DataInputStream dis = new DataInputStream(receptor.getInputStream());
            opcion = dis.readInt();
        } catch (IOException ex) {
        }
        return opcion;
    }

    public static void enviarBoolean(Socket receptor, boolean opcion) {

        DataOutputStream dos;
        try {
            dos = new DataOutputStream(receptor.getOutputStream());
            dos.writeBoolean(opcion);
        } catch (IOException ex) {
        }
    }

    public static boolean recibirBoolean(Socket receptor) {

        boolean opcion = false;
        try {
            DataInputStream dis = new DataInputStream(receptor.getInputStream());
            opcion = dis.readBoolean();
        } catch (IOException ex) {
        }
        return opcion;
    }
}
