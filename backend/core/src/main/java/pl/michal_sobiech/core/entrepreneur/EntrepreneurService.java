package pl.michal_sobiech.core.entrepreneur;

import java.util.Optional;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.user.User;
import pl.michal_sobiech.core.user.UserEntity;
import pl.michal_sobiech.core.user.UserGroup;
import pl.michal_sobiech.core.user.UserRepository;
import pl.michal_sobiech.core.user.UserService;

@RequiredArgsConstructor
public class EntrepreneurService {

    private final UserService userService;
    private final UserRepository userRepository;

    @Transactional
    public Entrepreneur save(
            String email,
            String firstName,
            String lastName,
            String passwordRaw,
            String iban) {

        User user = userService.save(
                UserGroup.ENTREPRENEUR,
                email,
                firstName,
                lastName,
                passwordRaw,
                Optional.empty(),
                Optional.of(iban));

        return Entrepreneur.fromUserOrThrow(user);
    }

    public Entrepreneur getEnterpriseOwner(long appointmentId) {
        UserEntity user = userRepository.getEnterpriseOwner(appointmentId).orElseThrow();
        return Entrepreneur.fromUserOrThrow(User.fromEntity(user));
    }

    public Optional<Entrepreneur> getByUserId(long userId) {
        Optional<User> user = userService.findById(userId);
        return user.flatMap(Entrepreneur::fromUser);
    }

}
