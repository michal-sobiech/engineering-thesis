package pl.michal_sobiech.payout_worker.customer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pl.michal_sobiech.core.customer.CustomerService;
import pl.michal_sobiech.core.user.UserService;

@Configuration
public class CustomerConfig {

    @Bean
    public CustomerService customerService(
            UserService userService) {
        return new CustomerService(userService);
    }

}
