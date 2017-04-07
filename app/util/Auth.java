package util;

import controllers.base.EPCrudService;
import models.Hospital;
import models.Medicion;
import models.Medico;
import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

import javax.swing.*;
import java.math.BigInteger;
import java.util.Random;

/**
 * Created by felipeplazas on 4/2/17.
 */
public class Auth extends Action.Simple{

    protected static final EPCrudService<Medico> medicosCrud = new EPCrudService<>("medicos", Medico.class);
    @Override
    public F.Promise<Result> call(Http.Context ctx) throws Throwable {
        String token = getTokenFromHeader(ctx);
        if (token != null) {
            Medico user = medicosCrud.collection().findOne().as(Medico.class);
            if (user != null) {
                ctx.request().withUsername(user.getId());
                return delegate.call(ctx);
            }
        }
        Result unauthorized = Results.unauthorized("unauthorized");
        return F.Promise.pure(unauthorized);
    }

    private String getTokenFromHeader(Http.Context ctx) {
        String[] authTokenHeaderValues = ctx.request().headers().get("X-AUTH-TOKEN");
        if ((authTokenHeaderValues != null) && (authTokenHeaderValues.length == 1) && (authTokenHeaderValues[0] != null)) {
            return authTokenHeaderValues[0];
        }
        return null;
    }

}
