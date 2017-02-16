package models;

import models.base.IdObject;

import java.util.List;

/**
 * Created by felipeplazas on 2/11/17.
 */
public class HistoriaClinica extends IdObject{

    private List<Consejo> consejos;
    private List<Emergencia> emergencias;
    private List<Consulta> consultas;

    public List<Emergencia> getEmergencias() {
        return emergencias;
    }

    public void setEmergencias(List<Emergencia> emergencias) {
        this.emergencias = emergencias;
    }

    public List<Consejo> getConsejos() {
        return consejos;
    }

    public void setConcejosAutomaticos(List<Consejo> consejos) {
        this.consejos = consejos;
    }

    public void setConsejos(List<Consejo> consejos) {
        this.consejos = consejos;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }
}
