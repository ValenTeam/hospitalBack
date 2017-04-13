package controllers;

import controllers.base.EPController;
import play.mvc.*;
import util.SecurityManager;
import util.TokenAuth;

import javax.xml.ws.spi.http.HttpContext;
import java.io.File;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by felipeplazas on 2/9/17.
 */
public class Application extends EPController {

    public Result index() {
        return ok(new File("public/main/index.html"), true);
    }

    @With(TokenAuth.class)
    public Result loderIOApiKey(){
        try {
            SecurityManager.validatePermission("view-paciente", (Http.Context.current.get().flash().get("token")));
            return ok("loaderio-11abdaba767352016f8aa9a2ab72b8d7");
        } catch (Exception e){
            return error(e.getMessage());
        }
    }

}
