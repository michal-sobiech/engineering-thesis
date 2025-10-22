package pl.michal_sobiech.engineering_thesis.independent_end_user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.user.UserDomain;

@Getter
@RequiredArgsConstructor
public class IndependentEndUserDomain {

    private final long userId;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final String passwordHash;

    public static IndependentEndUserDomain fromUserDomain(UserDomain userDomain) {
        if (!(IndependentEndUserUtils.USER_GROUPS.contains(userDomain.getUserGroup()))) {
            throw new IllegalArgumentException("User is not an independent end user");
        }

        return new IndependentEndUserDomain(
                userDomain.getUserId(),
                userDomain.getUsername(),
                userDomain.getFirstName(),
                userDomain.getLastName(),
                userDomain.getPasswordHash());
    }

}
