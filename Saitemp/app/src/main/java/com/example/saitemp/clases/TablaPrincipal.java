package com.example.saitemp.clases;

public class TablaPrincipal {

    private int id;
    private String nombre;
    private int version;

    public TablaPrincipal(int id, String nombre, int version) {
        this.id = id;
        this.nombre = nombre;
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
