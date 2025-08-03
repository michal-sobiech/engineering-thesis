package pl.michal_sobiech.engineering_thesis.username_authentication_token;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class UsernameAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;

    public UsernameAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
    }

    public UsernameAuthenticationToken(Object principal) {
        super(new ArrayList<>());
        this.principal = principal;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public Object getCredentials() {
        return new NoCredentials();
    }

}
