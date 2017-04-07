package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.tasks.OnSuccessListener;
import play.*;
import play.libs.Json;
import play.mvc.*;

import util.Auth;
import views.html.*;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by felipeplazas on 2/9/17.
 */
public class Application extends Controller {

    static{
        try {
            FileInputStream serviceAccount = new FileInputStream("fir-auth-dba02-firebase-adminsdk-rgnzq-22319d469a.json");
            System.out.println("INITI");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
                    .setDatabaseUrl("https://fir-auth-dba02.firebaseio.com/")
                    .build();

            FirebaseApp.initializeApp(options);


        } catch (Exception e){ e.printStackTrace();}
    }

    public Result index() {
        return ok(new File("public/main/index.html"), true);
    }

    @With(Auth.class)
    public Result loderIOApiKey(){
        return Results.ok("loaderio-11abdaba767352016f8aa9a2ab72b8d7");
    }

}
