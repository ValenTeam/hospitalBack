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
        return Results.ok("loaderio-8a636c602445e3b59cd5cd8045e80043");
    }

}
