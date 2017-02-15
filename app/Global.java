import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.jongo.*;
import play.*;
import play.libs.*;
import com.avaje.ebean.Ebean;
import models.*;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by felipeplazas on 2/9/17.
 */

public class Global extends GlobalSettings {
    @Override
    public void onStart(Application app) {

    }

}
