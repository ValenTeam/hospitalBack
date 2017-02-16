package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.base.EPController;
import models.Medicion;
import models.Paciente;
import play.mvc.Result;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
import util.EPJson;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by felipeplazas on 2/11/17.
 */
public class MedicionController extends EPController {

    private final static int BUFFER_SIZE = 879900;
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

    public Result listByPaciente(String patientId){
        Iterable<Medicion> mediciones = medicosCrud.collection().find().limit(20).as(Medicion.class);
        return ok( mediciones );
    }

    public Result listByPatientWithinDates(String patientId, Long date1, Long date2){
        ObjectNode dateQuery = EPJson.object( "$gt", date1, "$lt", date2 );
        String query = EPJson.object( "idPaciente", patientId,
                "openTimestamp", dateQuery).toString();
        Iterable<Medicion> mediciones = medicosCrud.collection().find( query ).as(Medicion.class);
        return ok( query );
    }

    public Result procesarMedicion() {
        Medicion medicion = bodyAs(Medicion.class);
        medsBuffer[bufferIndex++] = medicion;
        if ( bufferIndex == BUFFER_SIZE ) {
               insertMediciones();
        }
        return ok();
    }

    private synchronized static void insertMediciones(){
        if (bufferIndex != BUFFER_SIZE && bufferIndex != 0)
            medicionesCrud.collection().insert( Arrays.copyOf(medsBuffer, bufferIndex) );
        else if (bufferIndex == BUFFER_SIZE)
            medicionesCrud.collection().insert( medsBuffer );
        bufferIndex = 0;
    }

}
