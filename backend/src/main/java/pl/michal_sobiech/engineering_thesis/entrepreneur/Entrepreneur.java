package pl.michal_sobiech.engineering_thesis.entrepreneur;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.user.UserDomain;
import pl.michal_sobiech.engineering_thesis.user.UserGroup;

@Getter
@RequiredArgsConstructor
public class Entrepreneur {

    private final long userId;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final String passwordHash;

    public static Entrepreneur fromUserDomain(UserDomain userDomain) {
        if (userDomain.getUserGroup() != UserGroup.ENTREPRENEUR) {
            throw new IllegalArgumentException("User is not an entrepreneur");
        }

        return new Entrepreneur(
                userDomain.getUserId(),
                userDomain.getUsername(),
                userDomain.getFirstName(),
                userDomain.getLastName(),
                userDomain.getPasswordHash());
    }

}
