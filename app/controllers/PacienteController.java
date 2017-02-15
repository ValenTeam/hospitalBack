package controllers;

import controllers.base.EPController;
import models.HistoriaClinica;
import models.Marcapasos;
import models.Paciente;
import play.mvc.Result;

/**
 * kjhfkjhfkhkhfdkhkdh
 */
public class PacienteController extends EPController {

    /**
     * Creates a paciente, we assume the request is done properly
     * @return created paciente object
     */
    public Result create() {
        Paciente paciente = bodyAs(Paciente.class);
        pacientesCrud.save(paciente);
        return ok(paciente);
    }


    /**
     * Finds all the (first 100) pacientes
     * @return OK 200 with a list that may be empty if there are no pacientes.
     */
    public Result listAll() {
        Iterable<Paciente> pacientes = pacientesCrud.collection().find().limit(100).as(Paciente.class);
        //Return a 200 response with all the hospitals serialized.
        return ok(pacientes);
    }

    /**
     * Find a paciente with the given id
     * @param id
     * @return OK 200 if Paciente exists, 400 ERROR if it doesn't
     */
    public Result findById(String id) {
        Paciente paciente = null;
        try {
            paciente = pacientesCrud.findById(id);
        } catch (Exception e) {
            return error("Object does not exist", 400);
        }
        return ok(paciente);
    }

    /**
     * Retorna la historia clinica de un paciente dado su id
     * @param id
     * @return OK 200 if Paciente exists, 400 ERROR if it doesn't
     */
    public Result findHistoriaById(String id) {
        Paciente paciente = null;
        HistoriaClinica historia = null;
        try {
            paciente = pacientesCrud.findById(id);
            historia = paciente.getHistoriaClinica();

        } catch (Exception e) {
            return error("Object does not exist", 400);
        }
        return ok(historia);
    }



}
