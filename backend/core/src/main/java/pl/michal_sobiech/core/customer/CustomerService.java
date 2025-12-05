package pl.michal_sobiech.core.customer;

import java.util.Optional;

import org.SwaggerCodeGenExample.model.CreateIndependentEndUserRequest;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.shared.user.User;
import pl.michal_sobiech.shared.user.UserGroup;
import pl.michal_sobiech.shared.user.UserService;

@RequiredArgsConstructor
public class CustomerService {

    private final UserService userService;

    public Customer save(CreateIndependentEndUserRequest request) {
        User user = userService.save(
                UserGroup.CUSTOMER,
                request.getEmail(),
                request.getFirstName(),
                request.getLastName(),
                request.getPassword(),
                Optional.empty(),
                Optional.empty());
        return Customer.fromUser(user);
    }

    public Optional<Customer> getByUserId(long userId) {
        return userService.findById(userId).map(Customer::fromUser);
    }

}
