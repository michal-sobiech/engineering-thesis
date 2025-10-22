package pl.michal_sobiech.engineering_thesis.user;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.independent_end_user.IndependentEndUserUtils;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> findById(long userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> findByEnterpriseIdAndUsername(long enterpriseId, String username) {
        return userRepository.findByEnterpriseIdAndUsername(enterpriseId, username);
    }

    public Optional<User> findIndepentendEndUserByEmail(String email) {
        return userRepository.findByUserGroupInAndUsername(IndependentEndUserUtils.USER_GROUPS, email);
    }

    public Optional<User> findAdminByUsername(String username) {
        List<UserGroup> userGroups = List.of(UserGroup.REGULAR_ADMIN, UserGroup.HEAD_ADMIN);
        return userRepository.findByUserGroupInAndUsername(userGroups, username);
    }

    public UserDomain save(
            UserGroup userGroup,
            String username,
            String firstName,
            String lastName,
            String passwordHash,
            Optional<Long> enterpriseId) {
        User user = new User(
                null,
                userGroup,
                username,
                firstName,
                lastName,
                passwordHash,
                enterpriseId.orElse(null));
        user = userRepository.save(user);
        return UserDomain.fromEntity(user);
    }

}
