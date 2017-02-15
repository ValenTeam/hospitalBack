package models;

import models.base.IdObject;

import java.util.List;

/**
 * Created by felipeplazas and je.ardila1501.
 */
public class Medico extends IdObject {

    private String name;
    private Integer registroMedico;
    private String empresaAfiliado;
    private List<Paciente> pacientes;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRegistroMedico() { return registroMedico; }

    public void setRegistroMedico(Integer registroMedico) {this.registroMedico = registroMedico; }

    public String getEmpresaAfiliado() {return empresaAfiliado;}

    public void setEmpresaAfiliado(String empresaAfiliado) {this.empresaAfiliado = empresaAfiliado;}

    public List<Paciente> getPacientes() {return pacientes;}

    public void setPacientes(List<Paciente> pacientes) {this.pacientes = pacientes;}
}

