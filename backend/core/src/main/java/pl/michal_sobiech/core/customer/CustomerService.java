package pl.michal_sobiech.core.customer;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.user.User;
import pl.michal_sobiech.core.user.UserGroup;
import pl.michal_sobiech.core.user.UserService;

@RequiredArgsConstructor
public class CustomerService {

    private final UserService userService;

    public Customer save(String email, String firstName, String lastName, String passwordRaw) {
        User user = userService.save(
                UserGroup.CUSTOMER,
                email,
                firstName,
                lastName,
                passwordRaw,
                Optional.empty(),
                Optional.empty());
        return Customer.fromUser(user);
    }

    public Optional<Customer> getByUserId(long userId) {
        return userService.findById(userId).map(Customer::fromUser);
    }

}
