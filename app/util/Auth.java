package util;

<<<<<<< HEAD
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
=======
import play.mvc.With;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
>>>>>>> eeac3370384544ec9b06940d2c1bcdd7afe34986

/**
 * Created by felipeplazas on 4/7/17.
 */
<<<<<<< HEAD
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
=======
@With(TokenAuth.class)
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {
    String value() default "";
}
>>>>>>> eeac3370384544ec9b06940d2c1bcdd7afe34986
