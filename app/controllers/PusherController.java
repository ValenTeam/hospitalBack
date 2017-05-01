package controllers;

import com.pusher.rest.Pusher;
import controllers.base.EPController;
import models.Emergencia;
import models.Medicion;
import models.Paciente;
import util.EPJson;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Created by felipeplazas on 5/1/17.
 */
public class PusherController extends EPController {

    public static Pusher pusher = new Pusher("334122", "9000a2bfc63c687333a0", "35af0a809b67cff4e3a8");

    static{
        pusher.setEncrypted(true);
        pusher.trigger("my-channel", "my-event", Collections.singletonMap("message", "hello world"));
    }

    public static void sendAlert(Emergencia emergencia){
        CompletableFuture.runAsync(() -> {
            try {
                Paciente paciente = pacientesCrud.findById(emergencia.getPatientId());
                Map<String, Object> data = EPJson.map("patient", emergencia.getPatientId(), "tipo", emergencia.getTipoMedida());
                pusher.trigger(paciente.getDoctorId(), "my-event", data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
