package bd;

import java.sql.*;

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
            System.out.println("error al cargar la bd  "+e.getMessage());
        }
    }
    public void disconnect() {
        try {
            conexion.close();
        } catch (Exception e) {
        }
    }
    public void insertUser(String name,String email, String pass) {
        try {
            String sql = "insert into usuario (nombre,email,password) values(?,?,?)";
            sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, name);
            sentencia.setString(2, pass);
            sentencia.executeUpdate();
            sentencia.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public boolean selectUser(String name, String pass) {
        boolean exist = false;
        try {
            String sql = "select email, password from usuario where email = ? and password = ?";
            sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, name);
            sentencia.setString(2, pass);
            res = sentencia.executeQuery();
            if (res.next()) {
                exist = true;
            }
        } catch (SQLException e) {
        }
        return exist;
    }
}

