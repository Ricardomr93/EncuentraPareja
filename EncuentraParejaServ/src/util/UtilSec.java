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

       public static SealedObject encapsularObjeto(PublicKey pubK, Object u) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, IllegalBlockSizeException {
        SealedObject so = null;
        Cipher c = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        c.init(Cipher.ENCRYPT_MODE, pubK);
        so = new SealedObject((Serializable) u, c);
        return so;
    }

    public static Object desencriptarObjeto(SealedObject so, PrivateKey cPriv) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, ClassNotFoundException, IllegalBlockSizeException, BadPaddingException {
        Cipher c = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        c.init(Cipher.DECRYPT_MODE, cPriv);
        Object obj = so.getObject(c);
        return obj;
    }

    public static String encriptarTexto(String texto, String cifrado, SecretKey cs) {
        String textocif = "";
        try {
            Cipher c = Cipher.getInstance(cifrado);
            c.init(Cipher.ENCRYPT_MODE, cs);
            byte[] textoPlano = texto.getBytes();
            byte[] textoCifrado = c.doFinal(textoPlano);
            textocif = new String(textoCifrado);
        } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
        }
        return textocif;
    }

    public static String desecriptar(String texto, String cifrado, SecretKey clave) {
        String textocif = "";
        try {
            Cipher c = Cipher.getInstance(cifrado);
            c.init(Cipher.DECRYPT_MODE, clave);
            byte[] textoCifrado = texto.getBytes();
            byte[] Desencriptado = c.doFinal(textoCifrado);
            textocif = new String(Desencriptado);
        } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
        }
        return textocif;
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
            keyGen = KeyPairGenerator.getInstance("RSA");
            SecureRandom numero = SecureRandom.getInstance("SHA1PRNG");
            keyGen.initialize(5120, numero);
            par = keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
        }
        return par;
    }
}
