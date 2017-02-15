package models;

import models.base.IdObject;

import java.util.List;

/**
 * Created by je.ardila1501 on 13/02/2017.
 */
public class MedicoEspecialista extends Medico {

    private List <String> especialidad;

    public List<String> getEspecialidad() {return especialidad;}

    public void setEspecialidad(List<String> especialidad) {this.especialidad = especialidad;}
}
