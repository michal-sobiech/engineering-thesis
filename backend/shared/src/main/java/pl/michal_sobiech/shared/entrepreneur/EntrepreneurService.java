package pl.michal_sobiech.shared.entrepreneur;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.shared.user.User;
import pl.michal_sobiech.shared.user.UserEntity;
import pl.michal_sobiech.shared.user.UserGroup;
import pl.michal_sobiech.shared.user.UserRepository;
import pl.michal_sobiech.shared.user.UserService;

@Service
@RequiredArgsConstructor
public class EntrepreneurService {

    private final UserService userService;
    private final UserRepository userRepository;

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

    public Entrepreneur getEnterpriseOwner(long appointmentId) {
        UserEntity user = userRepository.getEnterpriseOwner(appointmentId).orElseThrow();
        return Entrepreneur.fromUser(User.fromEntity(user));
    }

    public Optional<Entrepreneur> getByUserId(long userId) {
        Optional<User> user = userService.findById(userId);
        return user.flatMap(Entrepreneur::fromUser);
    }

}
