package com.saitempsa.saitemp.clases;

public class IdsCompromiso {

    private int responsable_id;
    private int estado_id;

    public IdsCompromiso() {
    }

    public IdsCompromiso(int responsable_id, int estado_id) {
        this.responsable_id = responsable_id;
        this.estado_id = estado_id;
    }

    public int getResponsable_id() {
        return responsable_id;
    }

    public void setResponsable_id(int responsable_id) {
        this.responsable_id = responsable_id;
    }

    public int getEstado_id() {
        return estado_id;
    }

    public void setEstado_id(int estado_id) {
        this.estado_id = estado_id;
    }
}
