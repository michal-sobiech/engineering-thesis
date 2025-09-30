package pl.michal_sobiech.engineering_thesis.user;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import pl.michal_sobiech.engineering_thesis.user.auth_principal.AuthPrincipal;

public class UserIdAuthentication extends AbstractAuthenticationToken {

    private final AuthPrincipal principal;

    @Override
    public AuthPrincipal getPrincipal() {
        return this.principal;
    }

    public UserIdAuthentication(AuthPrincipal principal) {
        super(new ArrayList<>());
        this.principal = principal;
        setAuthenticated(true);
    }

    public UserIdAuthentication(AuthPrincipal principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        setAuthenticated(true);
    }

    @Override
    public NoCredentials getCredentials() {
        return new NoCredentials();
    }

}