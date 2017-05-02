package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.base.EPController;
import models.*;
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
        medsBuffer[bufferIndex++] = medicion;
        if(medicion.getColorMedicion().equals(Medicion.ColorMedicion.AMARILLO)){
            try{
                yellowConsejo(medicion);
            } catch (Exception e){
                return error(e.getMessage());
            }
        }
        if ( bufferIndex == BUFFER_SIZE ) {
            insertMediciones();
        }
        return ok("OK");
    }

    private static void yellowConsejo(Medicion medicion) throws Exception{
        if(medicion.getColorMedicion().equals(Medicion.ColorMedicion.AMARILLO)){
            Consejo consejo = new Consejo();
            Medicion.TipoMedida tipoMedida = medicion.getTipoMedicion();
            if (tipoMedida.equals(Medicion.TipoMedida.CARDIACA))
                consejo.setMensaje( Consejo.m1 );
            else if(tipoMedida.equals(Medicion.TipoMedida.ESTRES))
                consejo.setMensaje( Consejo.m2 );
            else if(tipoMedida.equals(Medicion.TipoMedida.PRESION))
                consejo.setMensaje( Consejo.m3 );

            //Paciente paciente = pacientesCrud.findById( medicion.getIdPaciente() );
            IFachada paciente = pacientesCrud.findById( medicion.getIdPaciente() );
            if(paciente !=null) {
                if (tipoMedida.equals(Medicion.TipoMedida.CARDIACA))
                    paciente.setFrecuenciaActual(medicion.getValorMedicion());
                else if(tipoMedida.equals(Medicion.TipoMedida.ESTRES))
                    paciente.setEstresActual(medicion.getValorMedicion());
                else if(tipoMedida.equals(Medicion.TipoMedida.PRESION))
                    paciente.setPresionActual(medicion.getValorMedicion());
                consejo.setFecha(System.currentTimeMillis());
                paciente.getHistoriaClinica().getConsejos().add(consejo);
                pacientesCrud.save((Paciente) paciente);
            } else{
                throw new Exception("El paciente no existe");
            }
        }
    }

    @With(TokenAuth.class)
    public Result listByPaciente(String patientId){
        String sort = EPJson.string("openTimestamp", 1);
        String query = EPJson.string("idPaciente", patientId);
        Iterable<Medicion> mediciones = medicionesCrud.collection().find(query).sort(sort).limit(40).as(Medicion.class);
        return ok( mediciones );
    }

    public Result listByPatientWithinDates(String patientId, Long date1, Long date2){
        ObjectNode dateQuery = EPJson.object( "$gt", date1, "$lt", date2 );
        String query = EPJson.object( "idPaciente", patientId,
                "openTimestamp", dateQuery).toString();
        Iterable<Medicion> mediciones = medicionesCrud.collection().find( query ).as(Medicion.class);
        return ok( mediciones );
    }

    public synchronized static void insertMediciones(){
        if (bufferIndex != BUFFER_SIZE && bufferIndex != 0)
            medicionesCrud.collection().insert( Arrays.copyOf(medsBuffer, bufferIndex) );
        else if (bufferIndex == BUFFER_SIZE)
            medicionesCrud.collection().insert( medsBuffer );
        bufferIndex = 0;
    }

}