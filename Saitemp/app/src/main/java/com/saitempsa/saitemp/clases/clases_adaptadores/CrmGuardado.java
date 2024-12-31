package com.saitempsa.saitemp.clases.clases_adaptadores;

public class CrmGuardado {

    private int id;
    private String sede;
    private String proceso;
    private String solicitante;
    private String medio_atencion;
    private String nit;
    private String razon_social;


    public CrmGuardado(int id, String sede, String proceso, String solicitante,String medio_atencion, String nit, String razon_social ) {
        this.id = id;
        this.sede = sede;
        this.proceso = proceso;
        this.solicitante = solicitante;
        this.medio_atencion = medio_atencion;
        this.nit = nit;
        this.razon_social = razon_social;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public String getProceso() {
        return proceso;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public String getMedio_atencion() {
        return medio_atencion;
    }

    public void setMedio_atencion(String medio_atencion) {
        this.medio_atencion = medio_atencion;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }
}
