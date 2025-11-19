package pl.michal_sobiech.engineering_thesis.entrepreneur;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.enterprise_staff.EnterpriseStaff;
import pl.michal_sobiech.engineering_thesis.user.User;
import pl.michal_sobiech.engineering_thesis.user.UserGroup;

@Getter
@RequiredArgsConstructor
public class Entrepreneur implements EnterpriseStaff {

    private final long userId;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final String passwordHash;

    public static Entrepreneur fromUser(User user) {
        if (user.getUserGroup() != UserGroup.ENTREPRENEUR) {
            throw new IllegalArgumentException("User is not an entrepreneur");
        }

        return new Entrepreneur(
                user.getUserId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getPasswordHash());
    }
}
