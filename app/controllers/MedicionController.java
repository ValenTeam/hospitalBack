package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.base.EPController;
import models.Consejo;
import models.HistoriaClinica;
import models.Medicion;
import models.Paciente;
import play.libs.Json;
import play.mvc.Result;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
import util.EPJson;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by felipeplazas on 2/11/17.
 */
public class MedicionController extends EPController {

    private final static int BUFFER_SIZE = 300000;
    private static Medicion [] medsBuffer = new Medicion[BUFFER_SIZE];
    private static int bufferIndex = 0;

    static{
        FiniteDuration duration = Duration.create((long) 10, TimeUnit.SECONDS);
        FiniteDuration interval = Duration.create((long) 5, TimeUnit.MINUTES);
        play.libs.Akka.system().scheduler().schedule(
                duration, interval,
                () -> {
                    insertMediciones();
                }, play.libs.Akka.system().dispatcher()
        );
    }

    public Result procesarMedicion() {
//        System.out.println("INFO "+request().body().asJson();
//        try {
//            Medicion m =  Json.fromJson(request().body().asJson(), Medicion.class);
//        } catch ( Exception e){
//            e.printStackTrace();
//        }
        Medicion medicion = bodyAs(Medicion.class);
//        System.out.println(medicion.getIdPaciente());
//        System.out.println(medicion.getValorMedicion());
        medsBuffer[bufferIndex++] = medicion;
        if(medicion.getColorMedicion().equals(Medicion.ColorMedicion.AMARILLO)){
            Consejo consejo = new Consejo();
            Medicion.TipoMedida tipoMedida = medicion.getTipoMedicion();
            if (tipoMedida.equals(Medicion.TipoMedida.CARDIACA))
                consejo.setMensaje( Consejo.m1 );
            else if(tipoMedida.equals(Medicion.TipoMedida.ESTRES))
                consejo.setMensaje( Consejo.m2 );
            else if(tipoMedida.equals(Medicion.TipoMedida.PRESION))
                consejo.setMensaje( Consejo.m3 );

            Paciente paciente = pacientesCrud.findById( medicion.getIdPaciente() );
            if (paciente.getHistoriaClinica() == null){
                paciente.setHistoriaClinica( new HistoriaClinica() );
                paciente.getHistoriaClinica().setConsejos( new ArrayList<Consejo>() );
            } else if (paciente.getHistoriaClinica().getConsejos() == null){
                paciente.getHistoriaClinica().setConsejos( new ArrayList<Consejo>() );
            }

            paciente.getHistoriaClinica().getConsejos().add( consejo );
            pacientesCrud.save( paciente );
        }
        if ( bufferIndex == BUFFER_SIZE ) {
            insertMediciones();
        }
        return ok("OK");
    }

    public Result listByPaciente(String patientId){
        Iterable<Medicion> mediciones = medicosCrud.collection().find().limit(20).as(Medicion.class);
        return ok( mediciones );
    }

    public Result listByPatientWithinDates(String patientId, Long date1, Long date2){
        ObjectNode dateQuery = EPJson.object( "$gt", date1, "$lt", date2 );
        String query = EPJson.object( "idPaciente", patientId,
                "openTimestamp", dateQuery).toString();
        Iterable<Medicion> mediciones = medicionesCrud.collection().find( query ).as(Medicion.class);
        return ok( mediciones );
    }

    private synchronized static void insertMediciones(){
        if (bufferIndex != BUFFER_SIZE && bufferIndex != 0)
            medicionesCrud.collection().insert( Arrays.copyOf(medsBuffer, bufferIndex) );
        else if (bufferIndex == BUFFER_SIZE)
            medicionesCrud.collection().insert( medsBuffer );
        bufferIndex = 0;
    }

}