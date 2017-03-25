package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.*;
import play.libs.Json;
import play.mvc.*;

import views.html.*;

import java.io.File;

/**
 * Created by felipeplazas on 2/9/17.
 */
public class Application extends Controller {

    public Result index() {
        return ok("LISTO CORRIENDO");
    }

    public Result loderIOApiKey(){
        return Results.ok("loaderio-da969e99941af46fcd5b1b22bc82239e");
    }

}
