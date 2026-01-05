package pl.michal_sobiech.core.employee;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.user.User;
import pl.michal_sobiech.core.user.UserGroup;
import pl.michal_sobiech.core.user.UserRepository;
import pl.michal_sobiech.core.user.UserService;

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
        return userRepository.getEnterpriseEmployeesByAppointmentId(appointmentId)
                .stream()
                .map(User::fromEntity)
                .map(Employee::fromUser)
                .collect(Collectors.toList());
    }

    public List<Employee> getEnterpriseEmployeesByEntepriseId(long enterpriseId) {
        return userRepository.getEnterpriseEmployeesByEnterpriseId(enterpriseId)
                .stream()
                .map(User::fromEntity)
                .map(Employee::fromUser)
                .collect(Collectors.toList());
    }

}
