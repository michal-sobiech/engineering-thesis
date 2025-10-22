package pl.michal_sobiech.engineering_thesis.employee;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.user.UserDomain;
import pl.michal_sobiech.engineering_thesis.user.UserGroup;
import pl.michal_sobiech.engineering_thesis.user.UserRepository;
import pl.michal_sobiech.engineering_thesis.user.UserService;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final UserRepository userRepository;

    public EmployeeDomain save(
            long enterpriseId,
            String username,
            String firstName,
            String lastName,
            String password) {
        String passwordHash = passwordEncoder.encode(password);

        UserDomain user = userService.save(
                UserGroup.EMPLOYEE,
                username,
                firstName,
                lastName,
                passwordHash,
                Optional.of(enterpriseId));

        return EmployeeDomain.fromUserDomain(user);
    }

    public Optional<EmployeeDomain> findEmployeeByEnterpriseIdAndUsername(long enterpriseId, String username) {
        return userRepository.findByUserGroupAndUsername(UserGroup.EMPLOYEE, username)
                .map(UserDomain::fromEntity)
                .map(EmployeeDomain::fromUserDomain);
    }

    public boolean checkEmployeeUsernameExists(long entepriseId, String username) {
        return findEmployeeByEnterpriseIdAndUsername(entepriseId, username).isPresent();
    }

}
