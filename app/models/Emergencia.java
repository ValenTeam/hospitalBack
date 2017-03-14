package models;

import models.base.IdObject;

/**
 * Created by felipeplazas on 2/14/17.
 */
public class Emergencia extends IdObject {

    private String patientId;
    private Long createdTimestamp;
    private Medicion.TipoMedida tipoMedida;
    private boolean processed;

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public Long getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Long createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public Medicion.TipoMedida getTipoMedida() {
        return tipoMedida;
    }

    public void setTipoMedida(Medicion.TipoMedida tipoMedida) {
        this.tipoMedida = tipoMedida;
    }
}
