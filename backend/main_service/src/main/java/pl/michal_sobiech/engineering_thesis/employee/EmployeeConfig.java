package pl.michal_sobiech.engineering_thesis.employee;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pl.michal_sobiech.core.employee.EmployeeService;
import pl.michal_sobiech.core.user.UserRepository;
import pl.michal_sobiech.core.user.UserService;

@Configuration
public class EmployeeConfig {

    @Bean
    public EmployeeService employeeService(UserService userService, UserRepository userRepository) {
        return new EmployeeService(userService, userRepository);
    }

}
