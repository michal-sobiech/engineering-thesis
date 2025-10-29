package pl.michal_sobiech.engineering_thesis.employee;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.user.User;
import pl.michal_sobiech.engineering_thesis.user.UserGroup;
import pl.michal_sobiech.engineering_thesis.user.UserRepository;
import pl.michal_sobiech.engineering_thesis.user.UserService;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final UserService userService;
    private final UserRepository userRepository;

    public Employee save(
            long enterpriseId,
            String username,
            String firstName,
            String lastName,
            String password) {
        User user = userService.save(
                UserGroup.EMPLOYEE,
                username,
                firstName,
                lastName,
                password,
                Optional.of(enterpriseId));

        return Employee.fromUser(user);
    }

    public Optional<Employee> findEmployeeByEnterpriseIdAndUsername(long enterpriseId, String username) {
        return userRepository.findByUserGroupAndUsername(UserGroup.EMPLOYEE, username)
                .map(User::fromEntity)
                .map(Employee::fromUser);
    }

    public boolean checkEmployeeUsernameExists(long entepriseId, String username) {
        return findEmployeeByEnterpriseIdAndUsername(entepriseId, username).isPresent();
    }

}
