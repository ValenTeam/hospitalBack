package models;

import models.base.IdObject;

/**
 * Created by Rogelio Garcia on 15-Feb-17.
 */
public class Consejo extends IdObject{

    private String mensaje;
    private long fecha;

    public String getMensaje(){return mensaje;}

    public void setMensaje(String mensaje){this.mensaje = mensaje;}

    public long getFecha() {
        return fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }
}
