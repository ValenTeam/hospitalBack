package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.org.apache.xml.internal.resolver.readers.ExtendedXMLCatalogReader;
import controllers.base.EPController;
import models.Consejo;
import models.Medico;
import models.Paciente;
import play.mvc.Result;
import util.EPJson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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
        String query = EPJson.string("deleted", false);
        Iterable<Medico> listaMedicos = medicosCrud.collection().find(query).as(Medico.class);
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

    public Result delete(String id)  {
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
