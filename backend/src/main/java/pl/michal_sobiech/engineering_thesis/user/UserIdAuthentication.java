package pl.michal_sobiech.engineering_thesis.user;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class UserIdAuthentication extends AbstractAuthenticationToken {

    private final int userId;

    @Override
    public Integer getPrincipal() {
        return this.userId;
    }

    public UserIdAuthentication(Integer userId, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.userId = userId;
    }

    public UserIdAuthentication(Integer userId) {
        super(new ArrayList<>());
        this.userId = userId;
    }

    @Override
    public Object getCredentials() {
        return new NoCredentials();
    }

}