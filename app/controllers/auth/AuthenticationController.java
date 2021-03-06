package controllers.auth;

import controllers.base.EPController;
import controllers.base.EPCrudService;
import models.Medico;
import models.Paciente;
import models.auth.LoginCredentials;
import models.auth.SessionToken;
import models.base.IdObject;
import models.base.UserObject;
import org.apache.commons.codec.binary.Hex;
import play.mvc.Result;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
import util.EPJson;
import util.SecurityManager;
import java.security.MessageDigest;
import java.util.concurrent.TimeUnit;

/**
 * Created by felipeplazas on 4/6/17.
 */
public class AuthenticationController extends EPController {

    static{
        FiniteDuration duration = Duration.create((long) 5, TimeUnit.SECONDS);
        FiniteDuration interval = Duration.create((long) 1, TimeUnit.MINUTES);
        play.libs.Akka.system().scheduler().schedule(
                duration, interval,
                () -> {
                    checkTokenTimestamps();
                }, play.libs.Akka.system().dispatcher()
        );
    }

    private static void checkTokenTimestamps(){
        try {
            System.out.println("Checking tokens");
            long currentTime = System.currentTimeMillis();
            Iterable<SessionToken> it = tokensCrud.collection().find().as(SessionToken.class);
            for (SessionToken st : it)
                if (currentTime > st.getExpireTimeStamp())
                    tokensCrud.hardDelete("_id",st.getId());
        } catch (Exception e){ e.printStackTrace();}
    }

    public Result userLogIn(){
        try {
            LoginCredentials lc = bodyAs(LoginCredentials.class);
            String userGroup = "";
            IdObject idObj = null;
            switch (lc.getRole()) {
                case admin:
                    idObj = findUser(lc.getEmail(), lc.getPassword(), adminCrud, UserObject.class);
                    userGroup = "admins";
                    break;
                case medico:
                    idObj = findUser(lc.getEmail(), lc.getPassword(), medicosCrud, Medico.class);
                    userGroup = "medicos";
                    break;
                case paciente:
                    idObj = findUser(lc.getEmail(), lc.getPassword(), pacientesCrud, Paciente.class);
                    userGroup = "pacientes";
                    break;
                default:
                    return error("You are trying to authenticate with a not existing role.", 400);
            }
            if (idObj == null)
                return error("User does not exist or password is not correct.", 400);
            SessionToken token = tokensCrud.collection().findOne(EPJson.string("userId", idObj.getId() ) ).as(SessionToken.class);
            if (token == null) {
                token = new SessionToken();
                token.setUserId(idObj.getId());
                token.setToken(SecurityManager.generateSessionToken());
                token.setUserGroup(userGroup);
            }
            token.setExpireTimeStamp(System.currentTimeMillis() + (3600000 * 2));
            tokensCrud.save(token);
            return ok(token);
        } catch (Exception e){
            e.printStackTrace();
            return error("Bad request, "+e.getMessage());
        }
    }

    private <T> T findUser(String email, String password, EPCrudService crud, Class<T> clazz) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestBytes = md.digest(password.getBytes("UTF-8"));
            String md5Pass = Hex.encodeHexString(digestBytes);
            md.reset();
            digestBytes = md.digest((email + md5Pass).getBytes("UTF-8"));
            String md5 = Hex.encodeHexString(digestBytes);
            String query = EPJson.string("email", email, "password", md5);
            return crud.collection().findOne(query).as(clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getPasswordHash(String email, String password){
        try {
            MessageDigest md = null;
            md = MessageDigest.getInstance("MD5");
            byte[] digestBytes = md.digest(password.getBytes("UTF-8"));
            String md5Pass = Hex.encodeHexString(digestBytes);
            md.reset();
            digestBytes = md.digest((email + md5Pass).getBytes("UTF-8"));
            return Hex.encodeHexString(digestBytes);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
