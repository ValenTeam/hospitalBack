package models;

import models.base.IdObject;

import java.util.List;

/**
 * Created by felipeplazas on 2/15/17.
 */
public class Consulta extends IdObject{

    private List<String> examenes;
    private List<String> tratamientos;
    private String diagnostico;
    private Long timeStamp;

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public List<String> getExamenes() {
        return examenes;
    }

    public void setExamenes(List<String> examenes) {
        this.examenes = examenes;
    }

    public List<String> getTratamientos() {
        return tratamientos;
    }

    public void setTratamientos(List<String> tratamientos) {
        this.tratamientos = tratamientos;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }
}
