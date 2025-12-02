package pl.michal_sobiech.shared.user;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.shared.exceptions.exceptions.ConflictException;
import pl.michal_sobiech.shared.user.User;
import pl.michal_sobiech.shared.user.UserEntity;
import pl.michal_sobiech.shared.user.UserGroup;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> findById(long userId) {
        return userRepository.findById(userId).map(User::fromEntity);
    }

    public Optional<UserEntity> findByEnterpriseIdAndUsername(long enterpriseId, String username) {
        return userRepository.findByEnterpriseIdAndUsername(enterpriseId, username);
    }

    // TODO move to a different service?
    public Optional<UserEntity> findAdminByUsername(String username) {
        List<UserGroup> userGroups = List.of(UserGroup.REGULAR_ADMIN, UserGroup.HEAD_ADMIN);
        return userRepository.findByUserGroupInAndUsername(userGroups, username);
    }

    public User save(
            UserGroup userGroup,
            String username,
            String firstName,
            String lastName,
            String passwordRaw,
            Optional<Long> enterpriseId) {
        try {
            String passwordHash = passwordEncoder.encode(passwordRaw);
            UserEntity user = new UserEntity(
                    null,
                    userGroup,
                    username,
                    firstName,
                    lastName,
                    passwordHash,
                    enterpriseId.orElse(null));
            user = userRepository.save(user);
            return User.fromEntity(user);
        } catch (DataIntegrityViolationException exception) {
            throw new ConflictException(exception);
        }
    }

    public List<User> getAllInUserGroup(UserGroup userGroup) {
        return userRepository.findAllByUserGroup(userGroup)
                .stream()
                .map(User::fromEntity)
                .collect(Collectors.toList());
    }

    public void patch(
            long userId,
            Optional<String> username,
            Optional<String> firstName,
            Optional<String> lastName,
            Optional<String> passwordRaw) {
        UserEntity user = userRepository.findById(userId).orElseThrow();

        username.ifPresent(usernameString -> {
            user.setUsername(usernameString);
        });

        firstName.ifPresent(firstNameString -> {
            user.setFirstName(firstNameString);
        });

        lastName.ifPresent(lastNameString -> {
            user.setLastName(lastNameString);
        });

        passwordRaw.ifPresent(passwordRawString -> {
            String passwordHash = passwordEncoder.encode(passwordRawString);
            user.setPasswordHash(passwordHash);
        });

        userRepository.save(user);
    }

}
