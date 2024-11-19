package com.example.saitemp.clases.clases_adaptadores;

public class Evidencia {

    private int id;
    private int formulario_id;
    private String nombre;
    private String path;
    private String descripcion;

    public Evidencia() {

    }

    public Evidencia(int id, int formulario_id, String nombre, String path, String descripcion) {
        this.id = id;
        this.formulario_id = formulario_id;
        this.nombre = nombre;
        this.path = path;
        this.descripcion = descripcion;
    }


    public Evidencia(String nombre, String path, String descripcion) {
        this.nombre = nombre;
        this.path = path;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFormulario_id() {
        return formulario_id;
    }

    public void setFormulario_id(int formulario_id) {
        this.formulario_id = formulario_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}