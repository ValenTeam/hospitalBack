package models;

import models.base.IdObject;

/**
 * Created by Rogelio Garcia on 15-Feb-17.
 */
public class Consejo extends IdObject{

    public final static String m1 = "Se le recomienda acercarse a la clínica para recibir un diagnóstico " +
            "adecuado con su médico de cabecera. Si ya tiene un diagnóstico actualmente, recuerde ";
    public final static String m2 = "Evite un estilo de vida estresante, practique actividades como yoga";
    public final static String m3 = "Debe ser estricto con las horas de su medicación." +
            "En caso de no haber tenido medicación hasta ahora, puede solicitar una cita con su médico de cabecera, o médico especialista. ";

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
