package pl.michal_sobiech.engineering_thesis.scope_username_password_authentication;

import java.util.List;

import org.springframework.data.util.Pair;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class EnterpriseIdUsernamePasswordAuthentication extends AbstractAuthenticationToken {

    private final long enterpriseId;
    private final String username;
    private final String password;

    public EnterpriseIdUsernamePasswordAuthentication(long enterpriseId, String username, String password) {
        super(List.of());
        this.enterpriseId = enterpriseId;
        this.username = username;
        this.password = password;
        setAuthenticated(false);
    }

    @Override
    public Pair<Long, String> getPrincipal() {
        return Pair.of(enterpriseId, username);
    }

    @Override
    public String getCredentials() {
        return password;
    }

}
