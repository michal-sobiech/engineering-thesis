package pl.michal_sobiech.core.customer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.user.User;
import pl.michal_sobiech.core.user.UserGroup;

@Getter
@RequiredArgsConstructor
public class Customer {

    private final long userId;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final String passwordHash;

    public static Customer fromUser(User user) {
        if (user.getUserGroup() != UserGroup.CUSTOMER) {
            throw new IllegalStateException("User is not a customer");
        }

        return new Customer(
                user.getUserId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getPasswordHash());
    }

}
