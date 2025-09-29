package pl.michal_sobiech.engineering_thesis.security.authentication;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class StringUsernamePasswordAuthentication extends AbstractAuthenticationToken {

    private final String username;
    private final String password;

    @Override
    public String getPrincipal() {
        return this.username;
    }

    public StringUsernamePasswordAuthentication(String username, String password,
            Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.username = username;
        this.password = password;
    }

    public StringUsernamePasswordAuthentication(String username, String password) {
        super(new ArrayList<>());
        this.username = username;
        this.password = password;
    }

    @Override
    public String getCredentials() {
        return this.password;
    }

}
