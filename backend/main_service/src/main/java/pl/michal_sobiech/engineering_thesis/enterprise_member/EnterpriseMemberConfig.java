package pl.michal_sobiech.engineering_thesis.enterprise_member;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pl.michal_sobiech.core.employee.EmployeeService;
import pl.michal_sobiech.core.enterprise_member.EnterpriseMemberService;
import pl.michal_sobiech.core.entrepreneur.EntrepreneurService;

@Configuration
public class EnterpriseMemberConfig {

    @Bean
    public EnterpriseMemberService enterpriseMemberService(
            EntrepreneurService entrepreneurService,
            EmployeeService employeeService) {
        return new EnterpriseMemberService(entrepreneurService, employeeService);
    }

}
