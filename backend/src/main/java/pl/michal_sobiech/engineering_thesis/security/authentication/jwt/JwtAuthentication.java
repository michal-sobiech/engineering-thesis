package pl.michal_sobiech.engineering_thesis.security.authentication.jwt;

import java.util.List;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import pl.michal_sobiech.engineering_thesis.user.NoCredentials;

public class JwtAuthentication extends AbstractAuthenticationToken {

    private final String jwt;

    public JwtAuthentication(String jwt) {
        super(List.of());
        this.jwt = jwt;
        setAuthenticated(false);
    }

    @Override
    public String getPrincipal() {
        return jwt;
    }

    @Override
    public NoCredentials getCredentials() {
        return new NoCredentials();
    }

}
