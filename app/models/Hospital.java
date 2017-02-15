package models;

import models.base.IdObject;

//import models.base.IdObject;

/**
 * Created by felipeplazas on 2/9/17.
 */

public class Hospital extends IdObject {

    private String name;
    private String address;
    private Medico medico;

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

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }
}
