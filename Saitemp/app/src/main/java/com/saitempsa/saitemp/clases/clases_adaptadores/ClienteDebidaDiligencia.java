package com.saitempsa.saitemp.clases.clases_adaptadores;

public class ClienteDebidaDiligencia {

    private int id;
    private String nombre;
    private String nit_numero_documento;

    public ClienteDebidaDiligencia(int id, String nombre, String nit_numero_documento) {
        this.id = id;
        this.nombre = nombre;
        this.nit_numero_documento = nit_numero_documento;
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

    public String getNit_numero_documento() {
        return nit_numero_documento;
    }

    public void setNit_numero_documento(String nit_numero_documento) {
        this.nit_numero_documento = nit_numero_documento;
    }
}
