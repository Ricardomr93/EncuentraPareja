package util;

import java.io.*;
import java.security.*;
import javax.crypto.*;

/**
 * @author Ricardo
 */
public class UtilSec {

    public static String hashPass(String txt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA1");
        byte text[] = txt.getBytes();
        md.update(text);
        byte resume[] = md.digest();
        String hex = "";
        for (int i = 0; i < resume.length; i++) {
            String h = Integer.toHexString(resume[i] & 0xFF);
            if (h.length() == 1) {
                hex += 0;
            }
            hex += h;
        }
        return hex;
    }

    public static Object desencapsularUser(PrivateKey privK, SealedObject so) {
        Object u = null;
        try {
            Cipher c = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            c.init(Cipher.DECRYPT_MODE, privK);
            u = so.getObject(c);
        } catch (IOException | ClassNotFoundException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
        }

        return u;
    }

    public static SealedObject encapsularObjeto(PublicKey pubK, Object u) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, IllegalBlockSizeException {
        SealedObject so;
        Cipher c = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        c.init(Cipher.ENCRYPT_MODE, pubK);
        so = new SealedObject((Serializable) u, c);
        return so;
    }

    public static KeyGenerator generarClave(String instance, int num) {
        KeyGenerator kg = null;
        try {
            kg = KeyGenerator.getInstance(instance);
            kg.init(num);
        } catch (NoSuchAlgorithmException e) {
        }
        return kg;
    }

    public static KeyPair generarclavePar() {
        KeyPairGenerator keyGen = null;
        KeyPair par = null;
        try {
            keyGen = KeyPairGenerator.getInstance("DSA");
            SecureRandom numero = SecureRandom.getInstance("SHA1PRNG");
            keyGen.initialize(1024, numero);
            par = keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
        }
        return par;
    }
}
