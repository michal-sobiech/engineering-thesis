package pl.michal_sobiech.engineering_thesis.entrepreneur;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.user.UserDomain;
import pl.michal_sobiech.engineering_thesis.user.UserGroup;
import pl.michal_sobiech.engineering_thesis.user.UserService;

@Service
@RequiredArgsConstructor
public class EntrepreneurService {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Transactional
    public Entrepreneur save(
            String email,
            String firstName,
            String lastName,
            String password) {
        String passwordHash = passwordEncoder.encode(password);

        UserDomain user = userService.save(
                UserGroup.ENTREPRENEUR,
                email,
                firstName,
                lastName,
                passwordHash,
                Optional.empty());

        return Entrepreneur.fromUserDomain(user);
    }
}
