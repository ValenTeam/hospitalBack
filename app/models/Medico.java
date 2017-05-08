package models;

import models.base.UserObject;

import java.util.List;

/**
 * Created by felipeplazas and je.ardila1501.
 */
public class Medico extends UserObject {


    private String name;
    private int phoneNumber;
    private String especialidad;


    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

