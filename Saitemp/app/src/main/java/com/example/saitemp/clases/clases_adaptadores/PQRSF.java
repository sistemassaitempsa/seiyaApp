package com.example.saitemp.clases.clases_adaptadores;

public class PQRSF {

    private int id;
    private String nombre;

    public PQRSF(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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
}