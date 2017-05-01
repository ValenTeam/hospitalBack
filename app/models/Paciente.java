package models;

import models.base.UserObject;

import java.util.List;

/**
 * Created by felipeplazas on 2/11/17.
 */
public class Paciente extends UserObject implements IFachada {

    public static final String VERDE ="VERDE";
    public static final String AMARILLO ="AMARILLO";
    public static final String ROJO ="ROJO";

    private String name;
    private String apellido;
    private long fechaNacimiento;
    private String cedula;
    private String address;
    private long marcapasosActual;
    private String antecedentes;
    private long presionActual;
    private long estresActual;
    private long frecuenciaActual;
    private HistoriaClinica historiaClinica;
    private Marcapasos marcapasos;
    private String estado;
    private String doctorId;

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getAntecedentes() {
        return antecedentes;
    }

    public void setAntecedentes(String antecedentes) {
        this.antecedentes = antecedentes;
    }

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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public long getMarcapasosActual() {
        return marcapasosActual;
    }

    public void setMarcapasosActual(long marcapasosActual) {
        this.marcapasosActual = marcapasosActual;
    }

    public long getPresionActual() {
        return presionActual;
    }

    public void setPresionActual(long presionActual) {
        this.presionActual = presionActual;
    }

    public long getEstresActual() {
        return estresActual;
    }

    public void setEstresActual(long estresActual) {
        this.estresActual = estresActual;
    }

    public long getFrecuenciaActual() {
        return frecuenciaActual;
    }

    public void setFrecuenciaActual(long frecuenciaActual) {
        this.frecuenciaActual = frecuenciaActual;
    }

    public long getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(long fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public List<Emergencia> getEmergencias(){
        return this.historiaClinica.getEmergencias();
    }

    @Override
    public List<Consejo> getConsejos(){
        return this.historiaClinica.getConsejos();
    }

    @Override
    public List<Consulta> getConsultas(){
        return this.historiaClinica.getConsultas();
    }

}
