package models;

import models.base.IdObject;

/**
 *
 */
public class Marcapasos extends IdObject {

    private double amplitud;

    private double duracion;

    private double sensibilidad;

    private String modo;

    public double getAmplitud() {
        return amplitud;
    }

    public void setAmplitud(double amplitud) {
        this.amplitud = amplitud;
    }

    public double getDuracion() {
        return duracion;
    }

    public void setDuracion(double duracion) {
        this.duracion = duracion;
    }

    public double getSensibilidad() {
        return sensibilidad;
    }

    public void setSensibilidad(double sensibilidad) {
        this.sensibilidad = sensibilidad;
    }

    public String getModo() {
        return modo;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }
}
