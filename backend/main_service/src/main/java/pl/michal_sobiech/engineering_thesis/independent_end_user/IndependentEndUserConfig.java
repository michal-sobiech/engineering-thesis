package pl.michal_sobiech.engineering_thesis.independent_end_user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pl.michal_sobiech.core.independent_end_user.IndependentEndUserService;
import pl.michal_sobiech.core.user.UserRepository;

@Configuration
public class IndependentEndUserConfig {

    @Bean
    public IndependentEndUserService independentEndUserService(UserRepository userRepository) {
        return new IndependentEndUserService(userRepository);
    }

}
