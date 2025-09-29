package pl.michal_sobiech.engineering_thesis.independent_end_user_details_service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.independent_end_user.IndependentEndUser;
import pl.michal_sobiech.engineering_thesis.independent_end_user.IndependentEndUserService;

@Service
@RequiredArgsConstructor
public class IndependentEndUserDetailsService implements UserDetailsService {

    private final IndependentEndUserService independentEndUserService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        IndependentEndUser independentEndUser = independentEndUserService.findByUsername(username).orElseThrow();
        return User.builder()
                .username(username)
                .password(independentEndUser.getPasswordHash())
                .build();
    }

}
