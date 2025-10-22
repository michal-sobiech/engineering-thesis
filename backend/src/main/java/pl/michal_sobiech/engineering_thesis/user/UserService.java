package pl.michal_sobiech.engineering_thesis.user;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.employee.EmployeeDomain;
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

    public Optional<EmployeeDomain> findEmployeeByEnterpriseIdAndUsername(long enterpriseId, String username) {
        return userRepository.findByUserGroupAndUsername(UserGroup.EMPLOYEE, username)
                .map(UserDomain::fromEntity)
                .map(EmployeeDomain::fromUserDomain);
    }

    public boolean checkEmployeeUsernameExists(long entepriseId, String username) {
        return findEmployeeByEnterpriseIdAndUsername(entepriseId, username).isPresent();
    }

    public EmployeeDomain createEmployee(
            long enterpriseId,
            String username,
            String firstName,
            String lastName,
            String passwordHash) {
        User user = User.builder()
                .enterpriseId(enterpriseId)
                .username(username)
                .firstName(firstName)
                .lastName(lastName)
                .passwordHash(passwordHash)
                .build();
        user = userRepository.save(user);
        UserDomain userDomain = UserDomain.fromEntity(user);
        return EmployeeDomain.fromUserDomain(userDomain);
    }

}
