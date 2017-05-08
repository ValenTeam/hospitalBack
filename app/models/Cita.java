package models;

import models.base.IdObject;

/**
 * Created by felipeplazas on 5/8/17.
 */
public class Cita extends IdObject {

    private Medico medico;
    private String patientId;
    private long fecha;

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public long getFecha() {
        return fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }
}
