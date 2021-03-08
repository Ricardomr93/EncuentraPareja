package bd;

import java.sql.*;
import java.util.ArrayList;
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

    public boolean activateUser(int id) {
        boolean canUpdate = true;
        try {
            connect();
            String sql = "update usuario set activado=? where id=?";
            sentencia = conexion.prepareStatement(sql);
            sentencia.setBoolean(1, true);
            sentencia.setInt(2, id);
            sentencia.executeUpdate();
            sentencia.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            canUpdate = false;
        } finally {
            disconnect();
        }
        return canUpdate;
    }

    public boolean actDesAdminUser(int id,boolean admin) {
        boolean canUpdate = true;
        try {
            connect();
            String sql = "update usuario set admin=? where id=?";
            sentencia = conexion.prepareStatement(sql);
            sentencia.setBoolean(1, admin);
            sentencia.setInt(2, id);
            sentencia.executeUpdate();
            sentencia.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            canUpdate = false;
        } finally {
            disconnect();
        }
        return canUpdate;
    }

    public boolean AniadirUser(User u) {
        boolean exist = false;
        connect();
        try {
            String sql = "insert into usuario (nombre,email,password,activado,admin) values (?,?,?,?,?)";
            sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, u.getName());
            sentencia.setString(2, u.getEmail());
            sentencia.setString(3, u.getPass());
            sentencia.setBoolean(4, u.isActive());
            sentencia.setBoolean(5, u.isAdmin());
            sentencia.executeUpdate();
            sentencia.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            exist = true;
        } finally {
            disconnect();
        }
        return exist;
    }

    public boolean RegisUser(String name, String email, String pass) {
        boolean exist = false;
        connect();
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
        } finally {
            disconnect();
        }
        return exist;
    }

    public boolean deleteUser(int id) {
        connect();
        boolean canDelete = true;
        try {
            String sql = "delete from usuario where id = ? ";
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, id);
            sentencia.executeUpdate();
            sentencia.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            canDelete = false;
        } finally {
            disconnect();
        }
        return canDelete;
    }

    public boolean updateUser(User u) {
        boolean canUpdate = true;
        try {
            connect();
            String sql = "update usuario set nombre=?,email=?,password =?,activado=?,admin=? where id=?";
            sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, u.getName());
            sentencia.setString(2, u.getEmail());
            sentencia.setString(3, u.getPass());
            sentencia.setBoolean(4, u.isActive());
            sentencia.setBoolean(5, u.isAdmin());
            sentencia.setInt(6, u.getId());
            sentencia.executeUpdate();
            sentencia.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            canUpdate = false;
        } finally {
            disconnect();
        }
        return canUpdate;
    }

    public User selectUser(String email, String pass) {
        User u = null;
        try {
            connect();
            String sql = "select id,nombre,email,activado,admin from usuario where email = ? and password = ?";
            sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, email);
            sentencia.setString(2, pass);
            res = sentencia.executeQuery();
            if (res.next()) {
                boolean activate, admin;
                int id = res.getInt("id");
                String name = res.getString("nombre");
                activate = res.getBoolean("activado");
                admin = res.getBoolean("admin");
                u = new User(id, name, email, pass, activate, admin);
            }
            res.close();
            sentencia.close();
        } catch (SQLException e) {
        } finally {
            disconnect();
        }
        return u;
    }

    public ArrayList<User> listaUserSinPsw() {
        ArrayList<User> uList = new ArrayList<>();
        User u;
        try {
            connect();
            String sql = "Select * FROM usuario";
            sentencia = conexion.prepareStatement(sql);
            res = sentencia.executeQuery();
            while (res.next()) {
                boolean activate, admin;
                int id = res.getInt("id");
                String name = res.getString("nombre");
                activate = res.getBoolean("activado");
                String email = res.getString("email");
                String pass = res.getString("password");
                admin = res.getBoolean("admin");
                u = new User(id, name, email, pass, activate, admin);
                uList.add(u);
            }
            res.close();
            sentencia.close();
        } catch (SQLException e) {
        } finally {
            disconnect();
        }
        return uList;
    }

}
