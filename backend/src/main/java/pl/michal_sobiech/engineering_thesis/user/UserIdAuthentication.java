package pl.michal_sobiech.engineering_thesis.user;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class UserIdAuthentication extends AbstractAuthenticationToken {

    private final AuthPrincipal principal;

    @Override
    public AuthPrincipal getPrincipal() {
        return this.principal;
    }

    public UserIdAuthentication(AuthPrincipal principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
    }

    public UserIdAuthentication(AuthPrincipal principal) {
        super(new ArrayList<>());
        this.principal = principal;
    }

    @Override
    public Object getCredentials() {
        return new NoCredentials();
    }

}