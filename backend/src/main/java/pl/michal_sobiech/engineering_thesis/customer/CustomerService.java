package pl.michal_sobiech.engineering_thesis.customer;

import java.util.Optional;

import org.SwaggerCodeGenExample.model.CreateIndependentEndUserRequest;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.user.User;
import pl.michal_sobiech.engineering_thesis.user.UserGroup;
import pl.michal_sobiech.engineering_thesis.user.UserService;

@Service
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
                Optional.empty());
        return Customer.fromUser(user);
    }

}
