package model;

import java.io.Serializable;

/**
 * @author Ricardo
 */
public class Preferencia implements Serializable {

    private int id;
    private String relacion;
    private boolean artisticos;
    private int deportivos;
    private int politicos;
    private String tqhijos;
    private String genero;
    private String interes;

    public Preferencia() {
    }

    public Preferencia(int id, String relacion, boolean artisticos, int deportivos, int politicos, String tqhijos, String genero, String interes) {
        this.id = id;
        this.relacion = relacion;
        this.artisticos = artisticos;
        this.deportivos = deportivos;
        this.politicos = politicos;
        this.tqhijos = tqhijos;
        this.genero = genero;
        this.interes = interes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRelacion() {
        return relacion;
    }

    public void setRelacion(String relacion) {
        this.relacion = relacion;
    }

    public boolean isArtisticos() {
        return artisticos;
    }

    public void setArtisticos(boolean artisticos) {
        this.artisticos = artisticos;
    }

    public int getDeportivos() {
        return deportivos;
    }

    public void setDeportivos(int deportivos) {
        this.deportivos = deportivos;
    }

    public int getPoliticos() {
        return politicos;
    }

    public void setPoliticos(int politicos) {
        this.politicos = politicos;
    }

    public String getTqhijos() {
        return tqhijos;
    }

    public void setTqhijos(String tqhijos) {
        this.tqhijos = tqhijos;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getInteres() {
        return interes;
    }

    public void setInteres(String interes) {
        this.interes = interes;
    }

    @Override
    public String toString() {
        return "Preferencia{" + "id=" + id + ", relacion=" + relacion + ", artisticos=" + artisticos + ", deportivos=" + deportivos + ", politicos=" + politicos + ", tqhijos=" + tqhijos + ", interes=" + interes + '}';
    }
}
