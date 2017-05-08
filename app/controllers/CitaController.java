package controllers;

import controllers.base.EPController;
import models.Cita;
import play.mvc.Result;
import util.EPJson;

/**
 * Created by felipeplazas on 5/8/17.
 */
public class CitaController extends EPController {

    public Result create(){
        Cita cita = bodyAs(Cita.class);
        return ok (citasCrud.save(cita));
    }

    public Result getCitasPaciente(String patientId){
        String query = EPJson.string("patientId", patientId);
        return ok(citasCrud.collection().find(query).as(Cita.class));
    }
}
