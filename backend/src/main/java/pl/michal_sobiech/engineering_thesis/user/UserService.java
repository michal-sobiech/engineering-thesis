package pl.michal_sobiech.engineering_thesis.user;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.exceptions.exceptions.ConflictException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<UserEntity> findById(long userId) {
        return userRepository.findById(userId);
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

}
