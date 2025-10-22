package pl.michal_sobiech.engineering_thesis.user;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class UserDomainAuthentication extends AbstractAuthenticationToken {

    private final UserDomain principal;

    @Override
    public UserDomain getPrincipal() {
        return this.principal;
    }

    public UserDomainAuthentication(UserDomain user) {
        super(new ArrayList<>());
        this.principal = user;
        setAuthenticated(true);
    }

    public UserDomainAuthentication(UserDomain user, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = user;
        setAuthenticated(true);
    }

    @Override
    public NoCredentials getCredentials() {
        return new NoCredentials();
    }

}