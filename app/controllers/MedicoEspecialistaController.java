package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.base.EPController;
import models.Marcapasos;
import models.Medico;
import models.MedicoEspecialista;
import models.Paciente;
import play.mvc.Result;


/**
 * Created by je.ardila1501
 */
public class MedicoEspecialistaController extends EPController {

    public Result create(){
        MedicoEspecialista medicoE = bodyAs(MedicoEspecialista.class);
        medicosCrud.save( medicoE );
        return ok(medicoE);
    }

    public Result listAll() {
        Iterable<MedicoEspecialista> listaMedicos = medicosCrud.collection().find().as(MedicoEspecialista.class);
        return ok(listaMedicos);
    }

    public Result findById(String id) {
        MedicoEspecialista medicoE = medicosCrud.collection().findOne().as(MedicoEspecialista.class);
        if (medicoE == null)
            return error("Object does not exist", 400);
        return ok(medicoE);
    }

    public Result changeValoresMarcapaso(String id){
        Marcapasos marcapaso = bodyAs(Marcapasos.class);
        marcapasosCrud.update(id, marcapaso);
        return ok(marcapaso);
    }
}

