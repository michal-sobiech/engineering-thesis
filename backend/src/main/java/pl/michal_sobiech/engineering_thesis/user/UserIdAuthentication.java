package pl.michal_sobiech.engineering_thesis.user;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import pl.michal_sobiech.engineering_thesis.user.auth_principal.AuthPrincipal;

public class UserIdAuthentication extends AbstractAuthenticationToken {

    private final AuthPrincipal principal;
    private final String jwt;

    @Override
    public AuthPrincipal getPrincipal() {
        return this.principal;
    }

    public UserIdAuthentication(AuthPrincipal principal, String jwt,
            Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.jwt = jwt;
    }

    public UserIdAuthentication(AuthPrincipal principal, String jwt) {
        super(new ArrayList<>());
        this.principal = principal;
        this.jwt = jwt;
    }

    @Override
    public String getCredentials() {
        return this.jwt;
    }

}