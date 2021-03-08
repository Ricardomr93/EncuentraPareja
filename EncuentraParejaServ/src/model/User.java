package model;

import java.io.Serializable;

/**
 * @author Ricardo
 */
public class User implements Serializable{
    private int id;
    private String name;
    private String email;
    private String pass;
    private boolean active;
    private boolean admin;

    public User() {
    }
    //recoge el usuario
    public User(int id,String name, String email, String pass, boolean active, boolean admin) {
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.active = active;
        this.admin = admin;
        this.id = id;
    }
    //uaurio para añadir
    public User(String name, String email, String pass) {
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.active = false;
        this.admin = false;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + ", email=" + email + ", pass=" + pass + ", active=" + active + ", admin=" + admin + '}';
    }
}
