package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.auth.AuthenticationController;
import controllers.base.EPController;
import models.Consejo;
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
            SecurityManager.validatePermission("admin", (Http.Context.current.get().flash().get("token")));
            Medico medico = null;
            medico = medicosCrud.findById(id);
            if (medico == null){
                return error("Object does not exist", 400);
            }
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

    /*

     */
    public Result findPacientes(String id) {
        Medico medico = medicosCrud.findById(id);
        if (medico == null)
            return error("Object does not exist", 400);
        if (medico.getPacientes() == null)
            return error("el medico con id: "+id+" No ha atendido ningun paciente");
        else
            return ok( medico.getPacientes() );
    }

    public Result createConsejo(String pacienteId, String medicoId){
        JsonNode node = request().body().asJson();
        JsonNode mensaje = node.get("msg");

        Consejo consejo=new Consejo();
        Paciente paciente = null;
        Medico medicoG = null;
        Date date = new Date();
        DateFormat hour = new SimpleDateFormat("HH:mm:ss");
        DateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
        String horaActual= hour.format(date);
        String fechaActual = fecha.format(date);
        try{
            paciente = pacientesCrud.findById(pacienteId);
            medicoG = medicosCrud.findById(medicoId);
            consejo.setMensaje("********************************************\n\n"+"Hora: "+horaActual +" - " + "Fecha: " + fechaActual + "\n"
            + "Hola: "+paciente.getName() + "  Hoy te hago las siguiente recomendacion para mejorar tu salud: " + "\n\n"
            + mensaje + "\n\n" + "Cordialmente tú medico: " + medicoG.getName() + "\n\n" + "********************************************");

            // FALTA PERSISTIR EL CONSEJO EN LA HISTORIA CLINICA
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return ok(consejo);
    }

}
