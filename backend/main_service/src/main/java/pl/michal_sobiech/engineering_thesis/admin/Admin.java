package pl.michal_sobiech.engineering_thesis.admin;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.shared.user.User;

@Getter
@RequiredArgsConstructor
public class Admin {

    private final long userId;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String passwordHash;

    public static Admin fromUser(User user) {
        if (!(AdminUtils.ADMIN_GROUPS.contains(user.getUserGroup()))) {
            throw new IllegalArgumentException("User is not an admin");
        }

        return new Admin(
                user.getUserId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getPasswordHash());
    }

}
