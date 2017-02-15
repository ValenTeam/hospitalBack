package controllers;


import controllers.base.EPController;
import models.MedicoGeneral;
import models.Paciente;
import play.mvc.Result;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by je.ardila1501.
 */
public class MedicoGeneralController extends EPController {


    public Result create(){
        MedicoGeneral medicoG = bodyAs(MedicoGeneral.class);
        medicosGeneralesCrud.save(medicoG);
        return ok(medicoG);
    }

    /*

    */
    public Result listAll() {

        Iterable<MedicoGeneral> listaMedicos = medicosGeneralesCrud.collection().find().as(MedicoGeneral.class);
        return ok(listaMedicos);
    }

    /*

     */
    public Result findById(String id) {
        MedicoGeneral medicoG = null;
        medicoG = medicosGeneralesCrud.findById(id);
        if (medicoG == null){
            return error("Object does not exist", 400);
        }
        return ok(medicoG);
    }

    public String createConsejo(String pacienteId, String mensaje, String medicoId){
        String consejo=null;
        Paciente paciente = null;
        MedicoGeneral medicoG = null;
        Date date = new Date();
        DateFormat hour = new SimpleDateFormat("HH:mm:ss");
        DateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
        String horaActual= hour.format(date);
        String fechaActual = fecha.format(date);

        try{
            paciente = pacientesCrud.findById(pacienteId);
            medicoG = medicosGeneralesCrud.findById(medicoId);
            consejo = "********************************************\n\n"+"Hora: "+horaActual +" - " + "Fecha: " + fechaActual + "\n";
            consejo = consejo + "Hola: "+paciente.getName() + "  Hoy te hago las siguiente recomendacion para mejorar tu salud: " + "\n\n";
            consejo = consejo + mensaje + "\n\n";
            consejo = consejo + "Cordialmente tú medico: " + medicoG.getName() + "\n\n" + "********************************************";

            // FALTA PERSISTIR EL CONSEJO EN LA HISTORIA CLINICA
        }
        catch (Exception e){
            //TODO error retorna Result
            //return error("no se ha podido enviar el consejo al paciente: " + pacienteId, 400);

        }
        return consejo;
    }


}
