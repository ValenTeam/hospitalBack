package controllers;

import controllers.base.EPController;
import models.Emergencia;
import models.HistoriaClinica;
import models.Paciente;
import play.mvc.Result;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
import util.EPJson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by felipeplazas on 2/14/17.
 */
public class EmergenciaController extends EPController {

    private final static int BUFFER_SIZE = 300000;
    private static Emergencia[] emergenciasBuffer = new Emergencia[BUFFER_SIZE];
    private static int bufferIndex = 0;

    static{
        FiniteDuration duration = Duration.create((long) 5, TimeUnit.SECONDS);
        FiniteDuration interval = Duration.create((long) 1, TimeUnit.SECONDS);
        play.libs.Akka.system().scheduler().schedule(
                duration, interval,
                () -> {
                    insertEmergencias();
                }, play.libs.Akka.system().dispatcher()
        );
        interval = Duration.create((long) 10, TimeUnit.MINUTES);
        play.libs.Akka.system().scheduler().schedule(
                duration, interval,
                () -> {
                    insertEmergenciesIntoPacientes();
                }, play.libs.Akka.system().dispatcher()
        );
    }

    public Result procesarEmergencia(){
        Emergencia emergencia = bodyAs(Emergencia.class);
        emergencia.setProcessed(false);
        synchronized (emergenciasBuffer){
            emergenciasBuffer[bufferIndex++] = emergencia;
            if ( bufferIndex == BUFFER_SIZE ) {
                CompletableFuture.runAsync(() -> {
                    insertEmergencias();
                });
            }
        }
        return ok( "processed" );
    }

    private static void insertEmergencias(){
        Emergencia [] emr;
        int index;
        synchronized (emergenciasBuffer){
            index = bufferIndex;
            emr = new Emergencia[bufferIndex];
            System.arraycopy( emergenciasBuffer, 0, emr, 0, bufferIndex);
            bufferIndex = 0;
        }
        if (index != BUFFER_SIZE && index != 0)
            emergenciasCrud.collection().insert( Arrays.copyOf(emr, index) );
        else if (index == BUFFER_SIZE)
            emergenciasCrud.collection().insert(emr);
    }

    private static void insertEmergenciesIntoPacientes(){
        System.out.println("LONG");
        String query = EPJson.string("processed",false);
        Iterator<Emergencia> emergencias = emergenciasCrud.collection().find(query).as(Emergencia.class).iterator();
        while (emergencias.hasNext()){
            Emergencia emergencia = emergencias.next();
            emergencia.setProcessed(true);
            Paciente paciente = pacientesCrud.findById( emergencia.getPatientId() );
            if (paciente.getHistoriaClinica() == null){
                paciente.setHistoriaClinica( new HistoriaClinica() );
                paciente.getHistoriaClinica().setEmergencias( new ArrayList<Emergencia>() );
            }
            if (paciente.getHistoriaClinica().getEmergencias() == null){
                paciente.getHistoriaClinica().setEmergencias( new ArrayList<Emergencia>() );
            }
            paciente.getHistoriaClinica().getEmergencias().add( emergencia );
            pacientesCrud.save( paciente );
            emergenciasCrud.save(emergencia);
        }
    }

}
