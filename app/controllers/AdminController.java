package controllers;

import controllers.auth.AuthenticationController;
import controllers.base.EPController;
import models.Medico;
import models.base.UserObject;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.With;
import util.SecurityManager;
import util.TokenAuth;

/**
 * Created by felipeplazas on 4/7/17.
 */
public class AdminController extends EPController {

//    @With(TokenAuth.class)
    public Result create(){
        try{
//            SecurityManager.validatePermission("admin", (Http.Context.current.get().flash().get("token")));
            UserObject user = bodyAs(UserObject.class);
            user.setPassword(AuthenticationController.getPasswordHash(user.getEmail(), user.getPassword()));
            return ok( adminCrud.save(user));
        } catch (Exception e){
            return error(e.getMessage());
        }
    }
}
