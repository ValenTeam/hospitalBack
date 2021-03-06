package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.auth.AuthenticationController;
import controllers.base.EPController;
import models.Consejo;
import models.IFachada;
import models.Medico;
import models.Paciente;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.With;
import util.EPJson;
import util.SecurityManager;
import util.TokenAuth;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by je.ardila1501
 */

public class MedicoController extends EPController {

    /*

     */
    @With(TokenAuth.class)
    public Result create(){
        try{
            SecurityManager.validatePermission("admin", (Http.Context.current.get().flash().get("token")));
            Medico medico = bodyAs(Medico.class);
            medico.setPassword(AuthenticationController.getPasswordHash(medico.getEmail(), medico.getPassword()));
            medicosCrud.save(medico);
            return ok(medico);
        } catch (Exception e){
            return error(e.getMessage());
        }
    }

    /*

     */
    @With(TokenAuth.class)
    public Result listAll() {
        try{
            SecurityManager.validatePermission("admin", (Http.Context.current.get().flash().get("token")));
            String query = EPJson.string("deleted", false);
            Iterable<Medico> listaMedicos = medicosCrud.collection().find(query).as(Medico.class);
            return ok(listaMedicos);
        } catch (Exception e){
            return error(e.getMessage());
        }
    }

    /*

     */
    @With(TokenAuth.class)
    public Result findById(String id) {
        try{
            SecurityManager.validatePermission("view-doctor", (Http.Context.current.get().flash().get("token")));
            Medico medico = medicosCrud.findById(id);
            if (medico == null)
                return error("Object does not exist", 400);
            return ok(medico);
        } catch (Exception e){
            return error(e.getMessage());
        }
    }

    public Result test() {
        try{
            Thread.sleep(2000);
        } catch (Exception e){
            e.printStackTrace();
        }
        String query = EPJson.string("deleted", false);
        Iterable<Medico> listaMedicos = medicosCrud.collection().find(query).as(Medico.class);
        return ok(listaMedicos);
    }

    /*

     */
    @With(TokenAuth.class)
    public Result findByName(String name) {
        try{
            SecurityManager.validatePermission("admin", (Http.Context.current.get().flash().get("token")));
            String query = EPJson.object("name", name).toString();
            Iterable<Medico> medicos = medicosCrud.collection().find(query).as(Medico.class);
            return ok(medicos);
        } catch (Exception e){
            return error(e.getMessage());
        }
    }

    /*

     */
    @With(TokenAuth.class)
    public Result findByRegistroMedico(Integer registro) {
        try{
            SecurityManager.validatePermission("admin", (Http.Context.current.get().flash().get("token")));
            String query = EPJson.object("registroMedico", registro).toString();
            Iterable<Medico> medicos = medicosCrud.collection().find(query).as(Medico.class);
            return ok(medicos);
        } catch (Exception e){
            return error(e.getMessage());
        }
    }

    @With(TokenAuth.class)
    public Result delete(String id)  {
        try{
            SecurityManager.validatePermission("admin", (Http.Context.current.get().flash().get("token")));
            medicosCrud.delete(id);
            return ok(id);
        } catch (Exception e){
            return error(e.getMessage());
        }
    }

    @With(TokenAuth.class)
    public Result findPacientes(String id) {
        try{
            SecurityManager.validatePermission("edit-paciente", (Http.Context.current.get().flash().get("token")));
        } catch (Exception e){ return error(e.getMessage()); }
        Medico medico = medicosCrud.findById(id);
        if (medico == null)
            return error("Object does not exist", 400);
        String query = EPJson.string("doctorId", id);
        return ok( pacientesCrud.collection().find(query).as(Paciente.class) );
    }

    @With(TokenAuth.class)
    public Result createConsejo(String pacienteId, String medicoId){
        try{
            SecurityManager.validatePermission("send-consejo", (Http.Context.current.get().flash().get("token")));
        } catch (Exception e){ return error(e.getMessage()); }
        Consejo consejo = bodyAs(Consejo.class);
        try{
            //Paciente paciente = pacientesCrud.findById(pacienteId);
            IFachada paciente = pacientesCrud.findById(pacienteId);
            consejo.setMensaje("Hola "+paciente.getName() + ". Hoy te hago las siguiente recomendación para mejorar tu salud: " + "\n"
            + consejo.getMensaje() + "\n" + "Cordialmente, Hospital Santa Fe");
            paciente.getHistoriaClinica().getConsejos().add(consejo);
            pacientesCrud.save((Paciente) paciente);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return ok(consejo);
    }

}
