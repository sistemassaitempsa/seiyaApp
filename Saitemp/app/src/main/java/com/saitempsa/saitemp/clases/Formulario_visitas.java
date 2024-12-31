package com.saitempsa.saitemp.clases;

import com.saitempsa.saitemp.clases.clases_adaptadores.Asistente;
import com.saitempsa.saitemp.clases.clases_adaptadores.Evidencia;

import java.util.List;

public class Formulario_visitas {

    private String sede;
    private int sede_id;
    private String proceso;
    private int proceso_id;
    private String solicitante;
    private int solicitante_id;
    private String medio_atencion;
    private int medio_atencion_id;
    private String numero_documento;
    private String cierra_radicado;
    private String razon_social;
    private String telefono;
    private String correo;
    private String visitante;
    private int visitante_id;
    private String cargo_visitante;
    private int cargo_visitante_id;
    private String visitado;
    private String cargo_visitado;
    private String objetivo;
    private String alcance;
    private String tema;
    private String estado;
    private int estado_id;
    private String pqrsf;
    private int pqrsf_id;
    private String responsable;
    private String correo_responsable;
    private int responsable_id;
    private String hora_inicio;
    private String observacion;
    private String latitud;
    private String longitud;
    private List<Asistente> asistentes;
    private List<Compromiso> compromisos;
    private List<Evidencia> evidencias;

    public Formulario_visitas() {
    }

    public Formulario_visitas(String sede, int sede_id, String proceso, int proceso_id, String solicitante, int solicitante_id, String medio_atencion, int medio_atencion_id, String numero_documento, String razon_social, String cierra_radicado, String telefono, String correo, String visitante, int visitante_id, String cargo_visitante, int cargo_visitante_id, String visitado, String cargo_visitado, String objetivo, String alcance, String tema, String estado, int estado_id, String pqrsf, int pqrsf_id, String responsable, int responsable_id, String hora_inicio, String observacion, String correo_responsable) {
        this.sede = sede;
        this.sede_id = sede_id;
        this.proceso = proceso;
        this.proceso_id = proceso_id;
        this.solicitante = solicitante;
        this.solicitante_id = solicitante_id;
        this.medio_atencion = medio_atencion;
        this.medio_atencion_id = medio_atencion_id;
        this.numero_documento = numero_documento;
        this.razon_social = razon_social;
        this.cierra_radicado = cierra_radicado;
        this.telefono = telefono;
        this.correo = correo;
        this.visitante = visitante;
        this.visitante_id = visitante_id;
        this.cargo_visitante = cargo_visitante;
        this.cargo_visitante_id = cargo_visitante_id;
        this.visitado = visitado;
        this.cargo_visitado = cargo_visitado;
        this.objetivo = objetivo;
        this.alcance = alcance;
        this.tema = tema;
        this.estado = estado;
        this.estado_id = estado_id;
        this.pqrsf = pqrsf;
        this.pqrsf_id = pqrsf_id;
        this.responsable = responsable;
        this.responsable_id = responsable_id;
        this.hora_inicio = hora_inicio;
        this.observacion = observacion;
        this.correo_responsable = correo_responsable;
    }


    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public int getSede_id() {
        return sede_id;
    }

    public void setSede_id(int sede_id) {
        this.sede_id = sede_id;
    }

    public String getProceso() {
        return proceso;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    public int getProceso_id() {
        return proceso_id;
    }

    public void setProceso_id(int proceso_id) {
        this.proceso_id = proceso_id;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public int getSolicitante_id() {
        return solicitante_id;
    }

    public void setSolicitante_id(int solicitante_id) {
        this.solicitante_id = solicitante_id;
    }

    public String getMedio_atencion() {
        return medio_atencion;
    }

    public void setMedio_atencion(String medio_atencion) {
        this.medio_atencion = medio_atencion;
    }

    public int getMedio_atencion_id() {
        return medio_atencion_id;
    }

    public void setMedio_atencion_id(int medio_atencion_id) {
        this.medio_atencion_id = medio_atencion_id;
    }

    public String getNumero_documento() {
        return numero_documento;
    }

    public void setNumero_documento(String numero_documento) {
        this.numero_documento = numero_documento;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }

    public String getCierra_radicado() {
        return cierra_radicado;
    }

    public void setCierra_radicado(String cierra_radicado) {
        this.cierra_radicado = cierra_radicado;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getVisitante() {
        return visitante;
    }

    public void setVisitante(String visitante) {
        this.visitante = visitante;
    }

    public int getVisitante_id() {
        return visitante_id;
    }

    public void setVisitante_id(int visitante_id) {
        this.visitante_id = visitante_id;
    }

    public String getCargo_visitante() {
        return cargo_visitante;
    }

    public void setCargo_visitante(String cargo_visitante) {
        this.cargo_visitante = cargo_visitante;
    }

    public int getCargo_visitante_id() {
        return cargo_visitante_id;
    }

    public void setCargo_visitante_id(int cargo_visitante_id) {
        this.cargo_visitante_id = cargo_visitante_id;
    }

    public String getVisitado() {
        return visitado;
    }

    public void setVisitado(String visitado) {
        this.visitado = visitado;
    }

    public String getCargo_visitado() {
        return cargo_visitado;
    }

    public void setCargo_visitado(String cargo_visitado) {
        this.cargo_visitado = cargo_visitado;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getAlcance() {
        return alcance;
    }

    public void setAlcance(String alcance) {
        this.alcance = alcance;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
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

    public String getPqrsf() {
        return pqrsf;
    }

    public void setPqrsf(String pqrsf) {
        this.pqrsf = pqrsf;
    }

    public int getPqrsf_id() {
        return pqrsf_id;
    }

    public void setPqrsf_id(int pqrsf_id) {
        this.pqrsf_id = pqrsf_id;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getCorreo_responsable() {
        return correo_responsable;
    }

    public void setCorreo_responsable(String correo_responsable) {
        this.correo_responsable = correo_responsable;
    }

    public int getResponsable_id() {
        return responsable_id;
    }

    public void setResponsable_id(int responsable_id) {
        this.responsable_id = responsable_id;
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public List<Asistente> getAsistentes() {
        return asistentes;
    }

    public void setAsistentes(List<Asistente> asistentes) {
        this.asistentes = asistentes;
    }

    public List<Compromiso> getCompromisos() {
        return compromisos;
    }

    public void setCompromisos(List<Compromiso> compromisos) {
        this.compromisos = compromisos;
    }

    public List<Evidencia> getEvidencias() {
        return evidencias;
    }

    public void setEvidencias(List<Evidencia> evidencias) {
        this.evidencias = evidencias;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String logitud) {
        this.longitud = logitud;
    }
}
