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
        Medico medicoG= new Medico();
        medicoG.setEmpresaAfiliado(medicoE.getEmpresaAfiliado());
        medicoG.setName(medicoE.getName());
        medicoG.setPacientes(medicoE.getPacientes());
        medicoG.setRegistroMedico(medicoE.getRegistroMedico());
        medicoG.setTipoMedico(Medico.TipoMedico.ESPECIALISTA);
        medicoE.setTipoMedico(Medico.TipoMedico.ESPECIALISTA);
        medicoG.setDeleted(false);
        medicosEspecialistasCrud.save(medicoE);
        medicosCrud.save(medicoG);
        String idtmp = medicoG.getId();
        medicoG.setId(medicoE.getId());
        System.out.println(medicoG.getId() +"\n" + medicoE.getId());
        medicosCrud.update(idtmp,medicoG);
        System.out.println("Now: " +"\n"+medicoG.getId() +"\n" + medicoE.getId());
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

    public Result changeValoresMarcapaso(String id){
        Marcapasos marcapaso  = null;
        JsonNode node = request().body().asJson();
//        JsonNode idMarcapasos = node.get("idMarcapasos");
//        if(idMarcapasos==null){
//        return error("Object does not exist", 400);
//        }
//        String id = idMarcapasos.toString();

        marcapaso=marcapasosCrud.findById(id);
        System.out.println(marcapaso+id);
        JsonNode amplitud = node.get("amplitud");
        if(amplitud!=null){
            double amp = Double.parseDouble(amplitud.toString());
            marcapaso.setAmplitud(amp);
        }

        JsonNode duracion = node.get("duracion");
        if(duracion!=null){
            double dur = Double.parseDouble(duracion.toString());
            marcapaso.setDuracion(dur);
        }

        JsonNode sensibilidad = node.get("sensibilidad");
        if(sensibilidad!=null){
            double sens = Double.parseDouble(sensibilidad.toString());
            marcapaso.setSensibilidad(sens);
        }

        JsonNode modo = node.get("modo");
        if(modo!=null){
            String mode = modo.toString();
            marcapaso.setModo(mode);
        }

        marcapasosCrud.save(marcapaso);
        return ok(marcapaso);
    }
}

