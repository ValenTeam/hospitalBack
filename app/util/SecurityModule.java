package util;

/**
 * Created by felipeplazas on 4/2/17.
 */
//import be.objectify.deadbolt.java.cache.HandlerCache;
import com.google.inject.AbstractModule;
import controllers.CustomAuthorizer;
//import controllers.DemoHttpActionAdapter;
import org.pac4j.cas.client.CasClient;
import org.pac4j.cas.client.CasProxyReceptor;
import org.pac4j.cas.config.CasConfiguration;
import org.pac4j.core.authorization.authorizer.RequireAnyRoleAuthorizer;
import org.pac4j.core.client.Clients;
import org.pac4j.core.client.direct.AnonymousClient;
import org.pac4j.core.config.Config;
import org.pac4j.http.client.direct.DirectBasicAuthClient;
import org.pac4j.http.client.direct.ParameterClient;
import org.pac4j.http.client.indirect.FormClient;
import org.pac4j.http.client.indirect.IndirectBasicAuthClient;
import org.pac4j.http.credentials.authenticator.test.SimpleTestUsernamePasswordAuthenticator;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.credentials.authenticator.JwtAuthenticator;
import org.pac4j.oauth.client.FacebookClient;
import org.pac4j.oauth.client.TwitterClient;
import org.pac4j.saml.client.SAML2Client;
import org.pac4j.saml.client.SAML2ClientConfiguration;
import play.Configuration;
import play.Environment;
import play.cache.CacheApi;

import java.io.File;

public class SecurityModule extends AbstractModule {

    private String baseUrl = "localhost:9000";

    @Override
    protected void configure() {
//        FacebookClient facebookClient = new FacebookClient("fbId", "fbSecret");
//        TwitterClient twitterClient = new TwitterClient("twId", "twSecret");

        FormClient formClient = new FormClient(baseUrl + "/loginForm", new SimpleTestUsernamePasswordAuthenticator());
        IndirectBasicAuthClient basicAuthClient = new IndirectBasicAuthClient(new SimpleTestUsernamePasswordAuthenticator());


//        ParameterClient parameterClient = new ParameterClient("token", new JwtAuthenticator("salt"));

        Clients clients = new Clients("http://localhost:9000/callback", basicAuthClient);
        Config config = new Config(clients);
        config.addAuthorizer("admin", new RequireAnyRoleAuthorizer<>("ROLE_ADMIN"));
        config.addAuthorizer("custom", new CustomAuthorizer());
//        config.setHttpActionAdapter(new DemoHttpActionAdapter());
        bind(Config.class).toInstance(config);
    }
}