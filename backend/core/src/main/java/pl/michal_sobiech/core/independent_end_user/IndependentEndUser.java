package pl.michal_sobiech.engineering_thesis.independent_end_user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.shared.user.User;

@Getter
@RequiredArgsConstructor
public class IndependentEndUser {

    private final long userId;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final String passwordHash;

    public static IndependentEndUser fromUser(User user) {
        if (!(IndependentEndUserUtils.USER_GROUPS.contains(user.getUserGroup()))) {
            throw new IllegalArgumentException("User is not an independent end user");
        }

        return new IndependentEndUser(
                user.getUserId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getPasswordHash());
    }

}
