package bd;

import java.sql.*;
import model.User;

/**
 * @author Ricardo
 */
public class Mysql {

    private Connection conexion;
    private PreparedStatement sentencia;
    private ResultSet res;

    public void connect() {
        try {
            // Cargar el driver
            Class.forName("com.mysql.jdbc.Driver");
            // Establecemos la conexion local con la BD ejemplo con el usuario y la
            // contrasenias
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/encuentrapareja", "root", "");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("error al cargar la bd  " + e.getMessage());
        }
    }

    public void disconnect() {
        try {
            conexion.close();
        } catch (Exception e) {
        }
    }

    public boolean insertUser(String name, String email, String pass) {
        boolean exist = false;
        try {
            String sql = "insert into usuario (nombre,email,password) values(?,?,?)";
            sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, name);
            sentencia.setString(2, email);
            sentencia.setString(3, pass);
            sentencia.executeUpdate();
            sentencia.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            exist = true;
        }
        return exist;
    }

    public User selectUser(String email, String pass) {
        User u = null;
        try {
            String sql = "select nombre,email, password,activado,admin from usuario where email = ? and password = ?";
            sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, email);
            sentencia.setString(2, pass);
            res = sentencia.executeQuery();
            if (res.next()) {
                boolean activate,admin;
                String name = res.getString(1);
                activate = res.getBoolean(4);
                admin = res.getBoolean(5);
                u = new User(name, email, pass,activate,admin);
            }
            sentencia.close();
        } catch (SQLException e) {
        }
        return u;
    }
    
}
