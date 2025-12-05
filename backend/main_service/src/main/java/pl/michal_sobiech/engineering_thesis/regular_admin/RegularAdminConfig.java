package pl.michal_sobiech.engineering_thesis.regular_admin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pl.michal_sobiech.core.regular_admin.RegularAdminsService;
import pl.michal_sobiech.core.user.UserService;

@Configuration
public class RegularAdminConfig {

    @Bean
    public RegularAdminsService regularAdminsService(UserService userService) {
        return new RegularAdminsService(userService);
    }

}
