package controllers;

import play.mvc.*;
import util.Auth;
import java.io.File;

/**
 * Created by felipeplazas on 2/9/17.
 */
public class Application extends Controller {

    public Result index() {
        return ok(new File("public/main/index.html"), true);
    }

    @With(Auth.class)
    public Result loderIOApiKey(){
        return Results.ok("loaderio-11abdaba767352016f8aa9a2ab72b8d7");
    }

}
