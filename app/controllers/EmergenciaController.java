package controllers;

import controllers.base.EPController;
import models.Emergencia;
import models.HistoriaClinica;
import models.Paciente;
import play.mvc.Result;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by felipeplazas on 2/14/17.
 */
public class EmergenciaController extends EPController {

    private final static int BUFFER_SIZE = 3000;
    private static Emergencia[] emergenciasBuffer = new Emergencia[BUFFER_SIZE];
    private static int bufferIndex = 0;

    static{
        FiniteDuration duration = Duration.create((long) 10, TimeUnit.SECONDS);
        FiniteDuration interval = Duration.create((long) 5, TimeUnit.SECONDS);
        play.libs.Akka.system().scheduler().schedule(
                duration, interval,
                () -> {
                    insertEmergencias();
                }, play.libs.Akka.system().dispatcher()
        );
    }

    public Result procesarEmergencia(){
        Emergencia emergencia = bodyAs(Emergencia.class);
        emergencia.setProcessed(false);
//        emergenciasCrud.save( emergencia );
        emergenciasBuffer[bufferIndex++] = emergencia;

        if ( bufferIndex == BUFFER_SIZE ) {
            CompletableFuture.runAsync(() -> {
                insertEmergencias();
            });
        }
        return ok( emergencia );
    }

    private static void insertEmergencias(){
        System.out.println("SAVING");
        for (int i = 0; i < emergenciasBuffer.length && emergenciasBuffer[i] != null; i++) {
            Paciente paciente = pacientesCrud.findById( emergenciasBuffer[i].getPatientId() );
            if (paciente.getHistoriaClinica() == null){
                paciente.setHistoriaClinica( new HistoriaClinica() );
                paciente.getHistoriaClinica().setEmergencias( new ArrayList<Emergencia>() );
            }

            if (paciente.getHistoriaClinica().getEmergencias() == null){
                paciente.getHistoriaClinica().setEmergencias( new ArrayList<Emergencia>() );
            }
            paciente.getHistoriaClinica().getEmergencias().add( emergenciasBuffer[i] );
            pacientesCrud.save( paciente );
        }
        if (bufferIndex != BUFFER_SIZE && bufferIndex != 0)
            emergenciasCrud.collection().insert( Arrays.copyOf(emergenciasBuffer, bufferIndex) );
        else if (bufferIndex == BUFFER_SIZE)
            emergenciasCrud.collection().insert(emergenciasBuffer);
        bufferIndex = 0;
    }

    private static void insertEmergenciesIntoPacientes(){
        System.out.println("SAVING");
        for (int i = 0; i < emergenciasBuffer.length && emergenciasBuffer[i] != null; i++) {
            Paciente paciente = pacientesCrud.findById( emergenciasBuffer[i].getPatientId() );
            if (paciente.getHistoriaClinica() == null){
                paciente.setHistoriaClinica( new HistoriaClinica() );
                paciente.getHistoriaClinica().setEmergencias( new ArrayList<Emergencia>() );
            }

            if (paciente.getHistoriaClinica().getEmergencias() == null){
                paciente.getHistoriaClinica().setEmergencias( new ArrayList<Emergencia>() );
            }
            paciente.getHistoriaClinica().getEmergencias().add( emergenciasBuffer[i] );
            pacientesCrud.save( paciente );
        }
        if (bufferIndex != BUFFER_SIZE && bufferIndex != 0)
            emergenciasCrud.collection().insert( Arrays.copyOf(emergenciasBuffer, bufferIndex) );
        else if (bufferIndex == BUFFER_SIZE)
            emergenciasCrud.collection().insert(emergenciasBuffer);
        bufferIndex = 0;
    }

}
