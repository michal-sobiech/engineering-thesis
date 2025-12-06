package pl.michal_sobiech.payout_worker.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pl.michal_sobiech.core.password_encoder.PasswordEncoder;
import pl.michal_sobiech.core.user.UserRepository;
import pl.michal_sobiech.core.user.UserService;

@Configuration
public class UserConfig {

    @Bean
    public UserService userService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return new UserService(userRepository, passwordEncoder);
    }

}
