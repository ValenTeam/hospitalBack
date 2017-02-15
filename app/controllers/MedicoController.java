package controllers;

import controllers.base.EPController;
import models.Medico;
import models.Paciente;
import play.mvc.Result;
import util.EPJson;

import java.util.List;

/**
 * Created by je.ardila1501
 */

public class MedicoController extends EPController {

    /*

     */
    public Result create(){
        Medico medico = bodyAs(Medico.class);
        medicosCrud.save(medico);
        return ok(medico);
    }

    /*

     */
    public Result listAll() {
        Iterable<Medico> listaMedicos = medicosCrud.collection().find().as(Medico.class);
        return ok(listaMedicos);
    }

    /*

     */
    public Result findById(String id) {
        Medico medico = null;
        medico = medicosCrud.findById(id);
        if (medico == null){
            return error("Object does not exist", 400);
        }
        return ok(medico);
    }

    /*

     */
    public Result findByName(String name) {
        String query = EPJson.object("name", name).toString();
        Iterable<Medico> medicos = medicosCrud.collection().find(query).as(Medico.class);
        return ok(medicos);
    }

    /*

     */
    public Result findByRegistroMedico(Integer registro) {
        String query = EPJson.object("registroMedico", registro).toString();
        Iterable<Medico> medicos = medicosCrud.collection().find(query).as(Medico.class);
        return ok(medicos);
    }

    /*

     */
    public Result findPacientes(String id) {
        Medico medico = null;
        medico = medicosCrud.findById(id);
        if (medico == null) {
            return error("Object does not exist", 400);
        }
        if (medico.getPacientes()==null){
            return error("el medico con id: "+id+" No ha atendido ningun paciente");
        }
        else{
            List<Paciente> listaPacientes=null;
            listaPacientes=medico.getPacientes();
            return ok(listaPacientes);
        }

    }

    public Result deletePaciente (String id)  {
        Medico medico = null;
        medico = medicosCrud.findById(id);
        try{
            medicosCrud.delete(id);
            return ok(medico);
        }
        catch (Exception e)
        {
            return error("no se ha podido eliminar el medico con id: "+id);
        }


    }
}
