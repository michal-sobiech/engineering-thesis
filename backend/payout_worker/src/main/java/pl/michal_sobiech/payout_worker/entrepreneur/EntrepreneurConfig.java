package pl.michal_sobiech.payout_worker.entrepreneur;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pl.michal_sobiech.core.entrepreneur.EntrepreneurService;
import pl.michal_sobiech.core.user.UserRepository;
import pl.michal_sobiech.core.user.UserService;

@Configuration
public class EntrepreneurConfig {

    @Bean
    public EntrepreneurService entrepreneurService(UserService userService, UserRepository userRepository) {
        return new EntrepreneurService(userService, userRepository);
    }

}
