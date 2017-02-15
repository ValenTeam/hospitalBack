package models;

import models.base.IdObject;

import java.util.List;

/**
 * Created by felipeplazas on 2/11/17.
 */
public class HistoriaClinica extends IdObject{

    private List<ConcejoAutomatico> concejosAutomaticos;
    private List<Emergencia> emergencias;

    public List<Emergencia> getEmergencias() {
        return emergencias;
    }

    public void setEmergencias(List<Emergencia> emergencias) {
        this.emergencias = emergencias;
    }

    public List<ConcejoAutomatico> getConcejosAutomaticos() {
        return concejosAutomaticos;
    }

    public void setConcejosAutomaticos(List<ConcejoAutomatico> concejosAutomaticos) {
        this.concejosAutomaticos = concejosAutomaticos;
    }
}
