package pl.michal_sobiech.engineering_thesis.employee;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean checkEmployeeUsernameExists(long enterpriseId, String username) {
        return employeeRepository.existsByEnterpriseIdAndUsername(enterpriseId, username);
    }

    public Employee createEmployee(long enterpriseId, String firstName, String lastName, String password,
            String username) {

        final String passwordHash = passwordEncoder.encode(password);

        final Employee employee = Employee.builder()
                .enterpriseId(enterpriseId)
                .firstName(firstName)
                .lastName(lastName)
                .passwordHash(passwordHash)
                .username(username)
                .build();
        return employeeRepository.save(employee);
    }

    public Employee getEmployee(long employeeId) {
        return employeeRepository.getReferenceById(employeeId);
    }

}
