package models;

import models.base.IdObject;

import java.awt.*;
import java.util.Date;

/**
 * Created by felipeplazas on 2/11/17.
 */
public class Medicion extends IdObject {

    public enum TipoMedida{
        CARDIACA, PRESION, ESTRES
    }

    public enum ColorMedicion{
        VERDE, AMARILLO, ROJO
    }

    private TipoMedida tipoMedicion;
    private ColorMedicion colorMedicion;
    private long valorMedicion;
    private String idPaciente;
    private long latitude;
    private long longitude;
    private long openTimestamp;
    private String digest;

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public TipoMedida getTipoMedicion() {
        return tipoMedicion;
    }

    public ColorMedicion getColorMedicion() {return colorMedicion;}

    public void setTipoMedicion(TipoMedida tipoMedicion) {
        this.tipoMedicion = tipoMedicion;
    }

    public void setColorMedicion(ColorMedicion colorMedicion) {this.colorMedicion = colorMedicion;}

    public long getValorMedicion() {
        return valorMedicion;
    }

    public void setValorMedicion(long valorMedicion) {
        this.valorMedicion = valorMedicion;
    }

    public String getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public long getOpenTimestamp() {
        return openTimestamp;
    }

    public void setOpenTimestamp(long openTimestamp) {
        this.openTimestamp = openTimestamp;
    }
}
