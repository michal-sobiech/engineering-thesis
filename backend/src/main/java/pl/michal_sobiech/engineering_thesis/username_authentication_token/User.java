package pl.michal_sobiech.engineering_thesis.username_authentication_token;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class User implements UserDetails {

    private final String username;
    private final String scope;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public User(String username, String scope, String password) {
        this.username = username;
        this.scope = scope;
        this.password = password;
        this.authorities = new ArrayList<>();
    }

}
