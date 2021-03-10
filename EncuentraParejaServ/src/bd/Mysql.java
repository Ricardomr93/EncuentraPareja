package bd;

import java.sql.*;
import java.util.ArrayList;
import model.Preferencia;
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

    public ArrayList<String> relacion() {
        ArrayList<String> rel = new ArrayList<>();
        try {
            connect();
            String sql = "select relacion from relacion";
            sentencia = conexion.prepareStatement(sql);
            res = sentencia.executeQuery();
            while (res.next()) {
                rel.add(res.getString("relacion"));
            }
            res.close();
            sentencia.close();
        } catch (SQLException e) {
        } finally {
            disconnect();
        }
        return rel;
    }

    public ArrayList<String> hijos() {
        ArrayList<String> hijos = new ArrayList<>();
        try {
            connect();
            String sql = "select hijos from tqhijos";
            sentencia = conexion.prepareStatement(sql);
            res = sentencia.executeQuery();
            while (res.next()) {
                hijos.add(res.getString("hijos"));
            }
            res.close();
            sentencia.close();
        } catch (SQLException e) {
        } finally {
            disconnect();
        }
        return hijos;
    }

    public ArrayList<String> interes() {
        ArrayList<String> interes = new ArrayList<>();
        try {
            connect();
            String sql = "select interes from interes";
            sentencia = conexion.prepareStatement(sql);
            res = sentencia.executeQuery();
            while (res.next()) {
                interes.add(res.getString("interes"));
            }
            res.close();
            sentencia.close();
        } catch (SQLException e) {
        } finally {
            disconnect();
        }
        return interes;
    }

    public synchronized boolean insertPrefs(Preferencia pref) {
        boolean exist = false;
        connect();
        try {
            String sql = "insert into preferencia (id,relacion,artisticos,deportivos,politicos,tqhijos,genero,interes) values (?,?,?,?,?,?,?,?)";
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, pref.getId());
            sentencia.setString(2, pref.getRelacion());
            sentencia.setBoolean(3, pref.isArtisticos());
            sentencia.setInt(4, pref.getDeportivos());
            sentencia.setInt(5, pref.getPoliticos());
            sentencia.setString(6, pref.getTqhijos());
            sentencia.setString(7, pref.getGenero());
            sentencia.setString(8, pref.getInteres());
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

    public synchronized ArrayList<ArrayList<Object>> mismasPref(User u, Preferencia p) {
        ArrayList<Object> uList = new ArrayList<>();
        ArrayList<Object> pList = new ArrayList<>();
        ArrayList<ArrayList<Object>> list = new ArrayList<>();
        try {
            connect();
            String sql = "SELECT *\n"
                    + "FROM usuario u JOIN preferencia p on u.id = p.id \n"
                    + "WHERE u.id <> " + u.getId() + " \n"
                    + "and p.relacion like '" + p.getRelacion() + "' and p.artisticos = ? \n"
                    + "and (p.politicos BETWEEN (" + p.getPoliticos() + ") and (" + p.getPoliticos() + "+20) or p.politicos BETWEEN (" + p.getPoliticos() + "-20)and (" + p.getPoliticos() + ")) \n"
                    + "and (p.deportivos BETWEEN (" + p.getDeportivos() + ") and (" + p.getDeportivos() + "+20) or p.deportivos BETWEEN (" + p.getDeportivos() + "-20)and (" + p.getDeportivos() + ")) \n"
                    + "and tqhijos = '" + p.getTqhijos() + "'\n"
                    + "and  (p.interes like (SELECT CONCAT(SUBSTR(pu.genero,1,1),'%') as gen\n"
                    + "from preferencia pu where pu.id=" + u.getId() + ") or p.interes like 'Ambos')";
            sentencia = conexion.prepareStatement(sql);
            sentencia.setBoolean(1, p.isArtisticos());
            res = sentencia.executeQuery();
            while (res.next()) {
                boolean activate, admin;
                int id = res.getInt("id");
                String name = res.getString("nombre");
                String email = res.getString("email");
                activate = res.getBoolean("activado");
                admin = res.getBoolean("admin");
                
                String relacion = res.getString("relacion");
                Boolean artisticos = res.getBoolean("artisticos");
                int deport = res.getInt("deportivos");
                int poli = res.getInt("politicos");
                String tqhi = res.getString("tqhijos");
                String genero = res.getString("genero");
                String interes = res.getString("interes");

                User us = new User(id, name, email, null, activate, admin);

                Preferencia pref = new Preferencia(id, relacion, artisticos, deport, poli, tqhi, genero, interes);
                uList.add(us);
                pList.add(pref);
            }
            list.add(uList);
            list.add(pList);
            res.close();
            sentencia.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            disconnect();
        }
        return list;
    }

    public synchronized Preferencia selectPrefes(int id) {
        Preferencia p = null;
        try {
            connect();
            String sql = "select * from usuario u JOIN preferencia p ON u.id = p.id where u.id = ?";
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, id);
            res = sentencia.executeQuery();
            if (res.next()) {
                String relacion = res.getString("relacion");
                Boolean artisticos = res.getBoolean("artisticos");
                int deport = res.getInt("deportivos");
                int poli = res.getInt("politicos");
                String tqhi = res.getString("tqhijos");
                String genero = res.getString("genero");
                String interes = res.getString("interes");
                p = new Preferencia(id, relacion, artisticos, deport, poli, tqhi, genero, interes);
            }
            res.close();
            sentencia.close();
        } catch (SQLException e) {
        } finally {
            disconnect();
        }
        return p;
    }

    public synchronized boolean primeraVez(int id) {
        boolean tieneFilas = true;
        try {
            connect();
            String sql = "select p.id from usuario u JOIN preferencia p ON u.id = p.id where u.id = ?";
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, id);
            res = sentencia.executeQuery();
            if (!res.next()) {
                tieneFilas = false;
            }
            res.close();
            sentencia.close();
        } catch (SQLException e) {
        } finally {
            disconnect();
        }
        return tieneFilas;
    }

    public void disconnect() {
        try {
            conexion.close();
        } catch (Exception e) {
        }
    }

    public synchronized boolean activateUser(int id) {
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

    public synchronized boolean actDesAdminUser(int id, boolean admin) {
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

    public synchronized boolean AniadirUser(User u) {
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

    public synchronized boolean RegisUser(String name, String email, String pass) {
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

    public synchronized boolean deleteUser(int id) {
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

    public synchronized boolean updateUser(User u) {
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

    public synchronized User selectUser(String email, String pass) {
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

    public synchronized ArrayList<User> listaUserSinPsw() {
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
                //  String pass = res.getString("password"); quitamos la password porque ocupa mucho y no arrancan bien las keys
                admin = res.getBoolean("admin");
                u = new User(id, name, email, null, activate, admin);
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
