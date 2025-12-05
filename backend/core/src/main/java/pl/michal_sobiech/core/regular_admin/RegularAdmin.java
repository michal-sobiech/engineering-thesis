package pl.michal_sobiech.core.regular_admin;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.user.UserGroup;

@Getter
@RequiredArgsConstructor
public class RegularAdmin {

    private final long userId;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String passwordHash;

    public static RegularAdmin fromUser(User user) {
        if (!user.getUserGroup().equals(UserGroup.REGULAR_ADMIN)) {
            throw new IllegalArgumentException("User is not a regular admin");
        }

        return new RegularAdmin(
                user.getUserId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getPasswordHash());
    }

}
