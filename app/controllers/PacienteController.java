package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.auth.AuthenticationController;
import controllers.base.EPController;
import models.*;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.With;
import util.EPJson;
import util.SecurityManager;
import util.TokenAuth;

import java.util.ArrayList;

/**
 * kjhfkjhfkhkhfdkhkdh
 */
public class PacienteController extends EPController {

    /**
     * Creates a paciente, we assume the request is done properly
     * @return created paciente object
     */
//    @With(TokenAuth.class)
    public Result create() {
        try{
//            SecurityManager.validatePermission("admin", (Http.Context.current.get().flash().get("token")));
            Paciente paciente = bodyAs(Paciente.class);
            paciente.setPassword(AuthenticationController.getPasswordHash(paciente.getEmail(), paciente.getPassword()));
            paciente.setHistoriaClinica(new HistoriaClinica());
            paciente.getHistoriaClinica().setConsejos(new ArrayList<>());
            paciente.getHistoriaClinica().setConsultas(new ArrayList<>());
            paciente.getHistoriaClinica().setEmergencias(new ArrayList<>());
            pacientesCrud.save(paciente);
            return ok(paciente);
        } catch (Exception e){
            return error(e.getMessage());
        }
    }

    public Result actualizar (String id){
        Paciente paciente = bodyAs(Paciente.class);
        pacientesCrud.save(paciente);
        return  ok( paciente );
    }

    /**
     * Finds all the (first 100) pacientes
     * @return OK 200 with a list that may be empty if there are no pacientes.
     */
    public Result listAll() {
        String query = EPJson.string("deleted", false);
        Iterable<Paciente> pacientes = pacientesCrud.collection().find(query).limit(100).as(Paciente.class);
        //Return a 200 response with all the hospitals serialized.
        return ok(pacientes);
    }

    /**
     * Find a paciente with the given id
     * @param id
     * @return OK 200 if Paciente exists, 400 ERROR if it doesn't
     */
    @With(TokenAuth.class)
    public Result findById(String id) {
        try{
            SecurityManager.validatePermission("view-patient", (Http.Context.current.get().flash().get("token")));
            Paciente paciente = null;
            try {
                paciente = pacientesCrud.findById(id);
            } catch (Exception e) {
                return error("Object does not exist", 400);
            }
            return ok(paciente);
        } catch (Exception e){
            return error(e.getMessage());
        }

    }

    /**
     * Retorna la historia clinica de un paciente dado su id
     * @param id
     * @return OK 200 if Paciente exists, 400 ERROR if it doesn't
     */
    public Result findHistoriaById(String id) {
        //Paciente paciente = pacientesCrud.findById(id);
        IFachada paciente = pacientesCrud.findById(id);
        if (paciente == null)
            return error("Object does not exist", 400);
        return ok( paciente.getHistoriaClinica() );
    }

     /** Updates a paciente, we assume the request is done properly
     * @return updated paciente object
     */
     @With(TokenAuth.class)
    public Result update(String id) {
        try{
            SecurityManager.validatePermission("edit-patient", (Http.Context.current.get().flash().get("token")));
            Paciente paciente = bodyAs(Paciente.class);
            pacientesCrud.update(id, paciente);
            return ok(paciente);
        } catch (Exception e){
            return error(e.getMessage());
        }
    }

    /** Deletes a paciente, we assume the request is done properly
     * @return ok  if the patient was deleted
     */
    public Result delete(String id)  {
        Paciente paciente = null;
        paciente = pacientesCrud.findById(id);
        try{
            pacientesCrud.delete(id);
            return ok(paciente);
        }
        catch (Exception e)
        {
            return error("no se ha podido eliminar el paciente con id: "+id);
        }
    }




}
