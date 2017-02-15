package controllers;

import controllers.base.EPController;
import models.Emergencia;
import models.HistoriaClinica;
import models.Paciente;
import play.mvc.Result;

import java.util.ArrayList;

/**
 * Created by felipeplazas on 2/14/17.
 */
public class EmergenciaController extends EPController {

    public Result procesarEmergencia(){
        Emergencia emergencia = bodyAs(Emergencia.class);
        emergenciasCrud.save( emergencia );

        Paciente paciente = pacientesCrud.findById( emergencia.getPatientId() );
        if (paciente.getHistoriaClinica() == null){
            paciente.setHistoriaClinica( new HistoriaClinica() );
            paciente.getHistoriaClinica().setEmergencias( new ArrayList<Emergencia>() );
        }

        paciente.getHistoriaClinica().getEmergencias().add( emergencia );
        pacientesCrud.save( paciente );
        return ok( emergencia );
    }

}
