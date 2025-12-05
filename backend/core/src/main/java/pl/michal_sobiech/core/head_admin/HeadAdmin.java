package pl.michal_sobiech.core.head_admin;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.user.User;
import pl.michal_sobiech.core.user.UserGroup;

@Getter
@RequiredArgsConstructor
public class HeadAdmin {

    private final long userId;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String passwordHash;

    public static HeadAdmin fromUser(User user) {
        if (!user.getUserGroup().equals(UserGroup.HEAD_ADMIN)) {
            throw new IllegalArgumentException("User is not in head admin group");
        }

        return new HeadAdmin(
                user.getUserId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getPasswordHash());
    }

}
