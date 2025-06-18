package com.saitempsa.saitemp.clases.clases_adaptadores;

import android.graphics.Bitmap;

public class Asistente {

    private int id;
    private int formulario_id;
    private String nombre;
    private String cargo;
    private String correo;
    private Bitmap firma;

    public Asistente() {
    }

    public Asistente(int id, int formulario_id, String nombre, String cargo, Bitmap firma) {
        this.id = id;
        this.formulario_id = formulario_id;
        this.nombre = nombre;
        this.cargo = cargo;
        this.firma = firma;
    }

    public Asistente(int id, int formulario_id, String nombre, String cargo, String correo, Bitmap firma) {
        this.id = id;
        this.formulario_id = formulario_id;
        this.nombre = nombre;
        this.cargo = cargo;
        this.correo = correo;
        this.firma = firma;
    }

    public int getFormulario_id() {
        return formulario_id;
    }

    public void setFormulario_id(int formulario_id) {
        this.formulario_id = formulario_id;
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

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Bitmap getFirma() {
        return firma;
    }

    public void setFirma(Bitmap firma) {
        this.firma = firma;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
