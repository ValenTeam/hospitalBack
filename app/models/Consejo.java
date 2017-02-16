package models;

/**
 * Created by Rogelio Garcia on 15-Feb-17.
 */
public class Consejo {
    public String mensaje;
    public Consejo (String mensaje){
        this.mensaje = mensaje;
    }
    public Consejo (Medicion.TipoMedida tipoMedida){
        String tipo = tipoMedida.toString();
        if (tipoMedida.equals(Medicion.TipoMedida.CARDIACA)){
            mensaje = "Se le recomienda acercarse a la clínica para recibir un diagnóstico adecuado con su médico de cabecera. \n" +
                    "Si ya tiene un diagnóstico actualmente, recuerde ";
        }else if(tipoMedida.equals(Medicion.TipoMedida.ESTRES)){
            mensaje = "Evite un estilo de vida estresante, practique actividades como yoga";
        }else if(tipoMedida.equals(Medicion.TipoMedida.PRESION)){
            mensaje = "Debe ser estricto con las horas de su medicación. \n" +
                    "En caso de no haber tenido medicación hasta ahora, puede solicitar una cita con su médico de cabecera, o médico especialista.";
        }
    }
    public String getMensaje(){return mensaje;}
    public void setMensaje(String mensaje){this.mensaje = mensaje;}
}
