package controllers;

import controllers.base.EPController;
import models.Marcapasos;
import models.MedicoEspecialista;
import models.Paciente;
import play.mvc.Result;

/**
 * Created by je.ardila1501
 */
public class MedicoEspecialistaController extends EPController {

    public Result create(){
        MedicoEspecialista medicoE = bodyAs(MedicoEspecialista.class);
        medicosEspecialistasCrud.save(medicoE);
        return ok(medicoE);
    }

    /*

    */
    public Result listAll() {
        Iterable<MedicoEspecialista> listaMedicos = medicosEspecialistasCrud.collection().find().as(MedicoEspecialista.class);
        return ok(listaMedicos);
    }

    /*

     */
    public Result findById(String id) {
        MedicoEspecialista medicoE = null;
        medicoE = medicosEspecialistasCrud.findById(id);
        if (medicoE == null){
            return error("Object does not exist", 400);
        }
        return ok(medicoE);
    }

    public Result changeValoresMarcapaso(String pacienteId, double pAmplitud, double pDuracion, double pSensibilidad, String pModo ){

        Paciente paciente = null;
        Marcapasos marcapasos = null;
        Marcapasos tmp=null;
        try{
            paciente = pacientesCrud.findById(pacienteId);
            tmp = paciente.getMarcapasos();
            marcapasos = paciente.getMarcapasos();
            marcapasos.setAmplitud(pAmplitud);
            marcapasos.setDuracion(pDuracion);
            marcapasos.setSensibilidad(pSensibilidad);
            marcapasos.setModo(pModo);
            marcapasosCrud.update(tmp.getId(),marcapasos);
        }
        catch(Exception e){
            return error("no se pudo actualizar la informacion del marcapasos del paciente con id: " + pacienteId, 400);
        }

        return ok(marcapasos);
    }
}

