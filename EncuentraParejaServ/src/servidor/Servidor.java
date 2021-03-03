package servidor;

import bd.Mysql;
import java.io.*;
import java.net.*;


/**
 * @author Ricardo
 */
public class Servidor {

    public static void main(String[] args){
        System.out.println("Servido arrancado...");
        try {
            ServerSocket ss = new ServerSocket(3500);
            Mysql bd = new Mysql();
            bd.connect();
            Socket cliente;
            while (true) {
                cliente = ss.accept();
                Hilo h = new Hilo(bd,cliente);
                h.start();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    

}
