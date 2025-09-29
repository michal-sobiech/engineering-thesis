package pl.michal_sobiech.engineering_thesis.security.authentication.independent_end_user;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.independent_end_user.IndependentEndUser;
import pl.michal_sobiech.engineering_thesis.independent_end_user.IndependentEndUserService;
import pl.michal_sobiech.engineering_thesis.security.authentication.UserUtils;

@Service
@RequiredArgsConstructor
public class IndependentEndUserDetailsService implements UserDetailsService {

    private final IndependentEndUserService independentEndUserService;

    @Override
    public User loadUserByUsername(String username) {
        IndependentEndUser independentEndUser = independentEndUserService.findByUsername(username).orElseThrow();
        return UserUtils.createUser(independentEndUser.getEmail(), independentEndUser.getPasswordHash());
    }

}
