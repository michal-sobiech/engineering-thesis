package pl.michal_sobiech.engineering_thesis.entrepreneur;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.user.User;
import pl.michal_sobiech.engineering_thesis.user.UserGroup;
import pl.michal_sobiech.engineering_thesis.user.UserService;

@Service
@RequiredArgsConstructor
public class EntrepreneurService {

    private final UserService userService;

    @Transactional
    public Entrepreneur save(
            String email,
            String firstName,
            String lastName,
            String passwordRaw) {

        User user = userService.save(
                UserGroup.ENTREPRENEUR,
                email,
                firstName,
                lastName,
                passwordRaw,
                Optional.empty());

        return Entrepreneur.fromUser(user);
    }
}
