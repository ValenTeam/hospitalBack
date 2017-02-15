package models;

import models.base.IdObject;

/**
 * Created by felipeplazas on 2/11/17.
 */
public class Paciente extends IdObject {

    private String name;
    private String address;
    private HistoriaClinica historiaClinica;
    private Marcapasos marcapasos;

    public HistoriaClinica getHistoriaClinica() {
        return historiaClinica;
    }

    public void setHistoriaClinica(HistoriaClinica historiaClinica) {
        this.historiaClinica = historiaClinica;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Marcapasos getMarcapasos() {
        return marcapasos;
    }

    public void setMarcapasos(Marcapasos marcapasos) {
        this.marcapasos = marcapasos;
    }
}
