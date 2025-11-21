package pl.michal_sobiech.engineering_thesis.security.oauth2;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

@Component
public class Oauth2Setupper {

    public HttpSecurity setUp(HttpSecurity httpSecurity) {
        try {
            httpSecurity.oauth2ResourceServer(oauth2Config -> oauth2Config.jwt(
                    Customizer.withDefaults()));
            return httpSecurity;
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

}
