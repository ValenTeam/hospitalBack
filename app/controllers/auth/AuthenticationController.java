package controllers.auth;

import controllers.base.EPController;
import models.Medico;
import models.auth.LoginCredentials;
import models.base.UserObject;
import org.apache.commons.codec.binary.Hex;
import play.mvc.Result;
import util.EPJson;

import java.security.MessageDigest;

/**
 * Created by felipeplazas on 4/6/17.
 */
public class AuthenticationController extends EPController {

    public Result userLogIn(){
        LoginCredentials lc = bodyAs(LoginCredentials.class);
        switch (lc.getRole()){
            case admin:
                break;
            case medico:
                Medico m = findMedico(lc.getEmail(), lc.getPassword());
                if (m == null){
                    //Ese usuario/clave no existe, paila.
                    return error("User does not exist or password is not correct.", 400);
                }
                return ok("Token: " +m.getName());

        }
        return error("bad gateway");
    }

    private Medico findMedico(String email, String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestBytes = md.digest(password.getBytes("UTF-8"));
            String md5Pass = Hex.encodeHexString(digestBytes);
            md.reset();
            digestBytes = md.digest((email + md5Pass).getBytes("UTF-8"));
            String md5 = Hex.encodeHexString(digestBytes);
            String query = EPJson.string("email", email, "password", md5);
            return medicosCrud.collection().findOne(query).as(Medico.class);
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
