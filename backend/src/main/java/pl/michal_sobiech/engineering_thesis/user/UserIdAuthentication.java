package pl.michal_sobiech.engineering_thesis.user;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class UserIdAuthentication extends AbstractAuthenticationToken {

    private final long userId;

    @Override
    public Long getPrincipal() {
        return this.userId;
    }

    public UserIdAuthentication(long userId, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.userId = userId;
    }

    public UserIdAuthentication(long userId) {
        super(new ArrayList<>());
        this.userId = userId;
    }

    @Override
    public Object getCredentials() {
        return new NoCredentials();
    }

}