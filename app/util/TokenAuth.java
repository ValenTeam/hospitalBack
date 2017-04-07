package util;

import controllers.Application;
import controllers.base.EPCrudService;
import models.Hospital;
import models.Medicion;
import models.Medico;
import models.auth.SessionToken;
import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

import java.math.BigInteger;
import java.util.Random;

/**
 * Created by felipeplazas on 4/2/17.
 */
public class TokenAuth extends Action.Simple {

    protected static final EPCrudService<SessionToken> tokensCrud = new EPCrudService<>("sessionTokens", SessionToken.class);

    @Override
    public F.Promise<Result> call(Http.Context ctx) throws Throwable {
        String token = getTokenFromHeader(ctx);
        if (token != null) {
            SessionToken sessionToken = tokensCrud.collection().findOne(EPJson.string("token", token)).as(SessionToken.class);
            if (sessionToken != null) {
                ctx.flash().put("token", sessionToken.getUserGroup());
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
