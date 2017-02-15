package controllers;

import controllers.base.EPController;
import models.Medicion;
import play.mvc.Result;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
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
