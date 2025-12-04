package pl.michal_sobiech.shared.employee;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.shared.user.User;
import pl.michal_sobiech.shared.user.UserGroup;
import pl.michal_sobiech.shared.user.UserRepository;
import pl.michal_sobiech.shared.user.UserService;

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
                Optional.of(enterpriseId),
                Optional.empty());

        return Employee.fromUser(user);
    }

    public Optional<Employee> findEmployeeByEnterpriseIdAndUsername(long enterpriseId, String username) {
        return userRepository.findByUserGroupAndUsername(UserGroup.EMPLOYEE, username)
                .map(User::fromEntity)
                .map(Employee::fromUser);
    }

    public boolean checkEmployeeUsernameExists(long enterpriseId, String username) {
        return findEmployeeByEnterpriseIdAndUsername(enterpriseId, username).isPresent();
    }

    public List<Employee> getEnterpriseEmployees(long appointmentId) {
        return userRepository.getEnterpriseEmployees(appointmentId)
                .stream()
                .map(User::fromEntity)
                .map(Employee::fromUser)
                .collect(Collectors.toList());
    }

}
