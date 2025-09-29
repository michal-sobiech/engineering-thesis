package pl.michal_sobiech.engineering_thesis.employee;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public boolean checkEmployeeUsernameExists(long enterpriseId, String username) {
        return employeeRepository.existsByEnterpriseIdAndUsername(enterpriseId, username);
    }

    public Employee createEmployee(long enterpriseId, String firstName, String lastName, String passwordHash,
            String username) {
        final Employee employee = Employee.builder()
                .enterpriseId(enterpriseId)
                .firstName(firstName)
                .lastName(lastName)
                .passwordHash(passwordHash)
                .username(username)
                .build();
        return employeeRepository.save(employee);
    }

}
