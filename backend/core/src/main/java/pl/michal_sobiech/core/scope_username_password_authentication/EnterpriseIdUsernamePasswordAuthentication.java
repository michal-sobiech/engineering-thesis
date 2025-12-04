package pl.michal_sobiech.engineering_thesis.scope_username_password_authentication;

import java.util.ArrayList;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class EnterpriseIdUsernamePasswordAuthentication extends AbstractAuthenticationToken {

    private final long enterpriseId;
    private final String username;
    private final String password;

    public EnterpriseIdUsernamePasswordAuthentication(long enterpriseId, String username, String password) {
        super(new ArrayList<>());
        this.enterpriseId = enterpriseId;
        this.username = username;
        this.password = password;
        setAuthenticated(false);
    }

    @Override
    public EnterpriseIdAndUsername getPrincipal() {
        return new EnterpriseIdAndUsername(enterpriseId, username);
    }

    @Override
    public String getCredentials() {
        return password;
    }

}
