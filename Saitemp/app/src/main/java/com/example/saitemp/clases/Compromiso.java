package com.example.saitemp.clases;

public class Compromiso {
    private int id;
    private int formulario_id;
    private String compromiso;
    private String responsable;
    private int responsable_id;
    private String estado;
    private int estado_id;
    private String  observacion;

    public Compromiso() {
    }

    public Compromiso(int id, String compromiso, String responsable, String estado, String observacion) {
        this.id = id;
        this.compromiso = compromiso;
        this.responsable = responsable;
        this.estado = estado;
        this.estado_id = estado_id;
        this.observacion = observacion;
    }

    public Compromiso(int id, int formulario_id, String compromiso, String responsable, String estado, String observacion) {
        this.id = id;
        this.formulario_id = formulario_id;
        this.compromiso = compromiso;
        this.responsable = responsable;

        this.estado = estado;

        this.observacion = observacion;
    }

    public Compromiso(String compromiso, String responsable,   String estado, String observacion) {
        this.compromiso = compromiso;
        this.responsable = responsable;

        this.estado = estado;

        this.observacion = observacion;
    }

    public Compromiso(int id, int formulario_id, String compromiso, String responsable, int responsable_id, String estado, int estado_id, String observacion) {
        this.id = id;
        this.formulario_id = formulario_id;
        this.compromiso = compromiso;
        this.responsable = responsable;
        this.responsable_id = responsable_id;
        this.estado = estado;
        this.estado_id = estado_id;
        this.observacion = observacion;
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

    public String getCompromiso() {
        return compromiso;
    }

    public void setCompromiso(String compromiso) {
        this.compromiso = compromiso;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getEstado_id() {
        return estado_id;
    }

    public void setEstado_id(int estado_id) {
        this.estado_id = estado_id;
    }

    public int getResponsable_id() {
        return responsable_id;
    }

    public void setResponsable_id(int responsable_id) {
        this.responsable_id = responsable_id;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
