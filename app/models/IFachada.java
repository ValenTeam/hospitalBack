package models;

import java.util.List;

/**
 * Created by John Edison on 23/04/2017.
 */
public interface IFachada {
    /**
     * Datos del Paciente
     */

    public String getName();

    public void setName(String name);

    public String getAddress();

    public void setAddress(String address);

    public String getApellido();

    public void setApellido(String apellido);

    public String getCedula();

    public void setCedula(String cedula);

    public long getFechaNacimiento();

    public void setFechaNacimiento(long fechaNacimiento);

    //*******************************************************************

    /**
     * Actualidad del paciente
     */

    public String getEstado();

    public void setEstado(String estado);

    public long getMarcapasosActual();

    public void setMarcapasosActual(long marcapasosActual);

    public long getPresionActual();

    public void setPresionActual(long presionActual);

    public long getEstresActual();

    public void setEstresActual(long estresActual);

    public long getFrecuenciaActual();

    public void setFrecuenciaActual(long frecuenciaActual);

    public String getMarcapasos();

    public void setMarcapasos(String marcapasos);
    //********************************************************************

    /**
     * Informacion "clinica" del Paciente
     */

    public String getAntecedentes();

    public void setAntecedentes(String antecedentes);

    public HistoriaClinica getHistoriaClinica();

    public void setHistoriaClinica(HistoriaClinica historiaClinica);

    public List<Emergencia> getEmergencias();

    public List<Consejo> getConsejos();

    public List<Consulta> getConsultas();

    //*****************************************************************
    /**
     * METODOS DE CLASES DISTINTAS A Paciente
     */

    //Clase: Medicion


}
