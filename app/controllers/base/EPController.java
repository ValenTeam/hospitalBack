package controllers.base;
import com.avaje.ebean.Model;
import com.avaje.ebean.Query;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import models.*;
import models.auth.SessionToken;
import models.auth.UserGroup;
import models.base.UserObject;
import org.jongo.Jongo;
import org.jongo.MongoCursor;
import play.Application;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import util.EPJson;
import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * By convention, we will always serve and receive JSON.
 * This class implements helper methods for usual request operations such as turning a request's
 * body into an object.
 * This class also manages the objectCrud objects,
 * see http://jongo.org/ for usage examples.
 * Created by felipeplazas on 2/9/17.
 */
public class EPController extends Controller {

    protected static final String CONTENT_TYPE = "application/json; charset=utf-8";

    /**
     * Creen una linea por cada nueva tabla (objeto) que se vaya a manejar
     */
    protected static final EPCrudService<Hospital> hospitalesCrud = new EPCrudService<>("hospitales", Hospital.class);
    protected static final EPCrudService<Medico> medicosCrud = new EPCrudService<>("medicos", Medico.class);
    protected static final EPCrudService<MedicoGeneral> medicosGeneralesCrud = new EPCrudService<>("medicosGenerales", MedicoGeneral.class);
    protected static final EPCrudService<MedicoEspecialista> medicosEspecialistasCrud = new EPCrudService<>("medicosEspecialistas", MedicoEspecialista.class);
    protected static final EPCrudService<Paciente> pacientesCrud = new EPCrudService<>("pacientes", Paciente.class);
    protected static final EPCrudService<Medicion> medicionesCrud = new EPCrudService<>("mediciones", Medicion.class);
    protected static final EPCrudService<Marcapasos> marcapasosCrud = new EPCrudService<>("marcapasos", Marcapasos.class);
    protected static final EPCrudService<HistoriaClinica> historiaClinicaCrud = new EPCrudService<>("historiaClinica", HistoriaClinica.class);
    protected static final EPCrudService<Emergencia> emergenciasCrud = new EPCrudService<>("emergencias", Emergencia.class);
    protected static final EPCrudService<SessionToken> tokensCrud = new EPCrudService<>("sessionTokens", SessionToken.class);
    protected static final EPCrudService<UserGroup> userGroupsCrud = new EPCrudService<>("userGroups", UserGroup.class);
    protected static final EPCrudService<UserObject> adminCrud = new EPCrudService<>("admins", UserObject.class);



    /**
     * Turns request body into an object of the class clazz
     * @param clazz
     * @param <T>
     * @return Instance of T
     */
    @Nonnull
    public static <T> T bodyAs(Class<T> clazz) {
        Http.RequestBody body = request().body();
        if (body == null || body.asJson() == null) {
            System.out.println("ERROR, this should never happen");
        }
        return Json.fromJson(body.asJson(), clazz);
    }

    /**
     * Returns a 200 OK status with the object passed as parameter serialized.
     * @param object
     * @return
     */
    protected static Result ok(Object object) {
        response().setContentType(CONTENT_TYPE);
        return object == null ? ok() : ok(Json.prettyPrint(Json.toJson(object)));
    }

    /**
     * Returns a 200 OK status with the list passed as parameter serialized.
     * @param list
     * @return
     */
    protected static Result ok(Iterable<?> list) {
        Iterable<?> toWrite = list == null ? new ArrayList<>() : list;
        response().setContentType(CONTENT_TYPE);
        Status res = ok(Json.prettyPrint(Json.toJson(toWrite)));
        if (toWrite instanceof MongoCursor)
            try {
                ((MongoCursor) toWrite).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        return res;
    }

    /**
     * Returns a 400 ERROR status with the string message passed as parameter.
     * @param message
     * @return
     */
    protected static Result error(String message) {
        return error(message, null);
    }

    /**
     * Returns a ERROR status with the string message.
     * and the code passed as parameter.
     * @param message
     * @param errorCode
     * @return
     */
    protected static Result error(String message, Integer errorCode) {
        response().setContentType(CONTENT_TYPE);
        String m = message != null ? message : "Request cannot be processed";
        int i = errorCode != null ? errorCode : 400;
        return status(i, EPJson.object("message", m, "code", errorCode));
    }

    /**
     * Returns a 200 OK message with the message passed as parameter.
     * @param message
     * @return
     */
    protected static Result success(String message) {
        response().setContentType(CONTENT_TYPE);
        return ok(EPJson.object("message", message));
    }

    /**
     * Returns a 200 OK message if the object passed as parameter is not null.
     * If it is, a 404 ERROR status is sent.
     * @param object
     * @return
     */
    protected static Result nonNull(Object object) {
        response().setContentType(CONTENT_TYPE);
        return object != null ? ok(Json.prettyPrint(Json.toJson(object))) : notFound();
    }

}
