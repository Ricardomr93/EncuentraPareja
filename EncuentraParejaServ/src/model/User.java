package model;

import java.io.Serializable;

/**
 * @author Ricardo
 */
public class User implements Serializable{
    private String name;
    private String email;
    private String pass;
    private boolean active;
    private boolean admin;

    public User() {
    }

    public User(String name, String email, String pass, boolean active, boolean admin) {
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.active = active;
        this.admin = admin;
    }

    public User(String name, String email, String pass) {
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.active = false;
        this.admin = false;
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
        return "User{" + "name=" + name + ", email=" + email + ", pass=" + pass + ", active=" + active + ", admin=" + admin + '}';
    }
    
}
