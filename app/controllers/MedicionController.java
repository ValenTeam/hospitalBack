package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.base.EPController;
import models.Consejo;
import models.HistoriaClinica;
import models.Medicion;
import models.Paciente;
import org.apache.commons.codec.binary.Hex;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.With;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
import util.EPJson;
import util.SecurityManager;
import util.TokenAuth;

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
        FiniteDuration interval = Duration.create((long) 1, TimeUnit.MINUTES);
        play.libs.Akka.system().scheduler().schedule(
                duration, interval,
                () -> {
                    insertMediciones();
                }, play.libs.Akka.system().dispatcher()
        );
    }

    public Result procesarMedicion() {
        Medicion medicion = bodyAs(Medicion.class);
        String hashInfo = medicion.getValorMedicion() +""+ medicion.getTipoMedicion();
        String localHash = new String ();
        try {
            localHash = Hex.encodeHexString( SecurityManager.hashDigest(hashInfo.getBytes() ) );
        } catch (Exception e){
            e.printStackTrace();
        }
        if (localHash != null && localHash.equals( medicion.getDigest() )){
            medicion.setDigest(null);
        }
        else{
            return error("integrity compromised.");
        }
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
            if(paciente !=null) {
                if (paciente.getHistoriaClinica() == null) {
                    paciente.setHistoriaClinica(new HistoriaClinica());
                    paciente.getHistoriaClinica().setConsejos(new ArrayList<Consejo>());
                } else if (paciente.getHistoriaClinica().getConsejos() == null) {
                    paciente.getHistoriaClinica().setConsejos(new ArrayList<Consejo>());
                }

                if (tipoMedida.equals(Medicion.TipoMedida.CARDIACA))
                    paciente.setFrecuenciaActual(medicion.getValorMedicion());
                else if(tipoMedida.equals(Medicion.TipoMedida.ESTRES))
                    paciente.setEstresActual(medicion.getValorMedicion());
                else if(tipoMedida.equals(Medicion.TipoMedida.PRESION))
                    paciente.setPresionActual(medicion.getValorMedicion());


                paciente.getHistoriaClinica().getConsejos().add(consejo);
                pacientesCrud.save(paciente);
            } else{
                return error("El paciente no existe");
            }
        }
        if ( bufferIndex == BUFFER_SIZE ) {
            insertMediciones();
        }
        return ok("OK");
    }

    @With(TokenAuth.class)
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