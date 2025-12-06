package pl.michal_sobiech.core.entrepreneur;

import java.util.Optional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.enterprise_member.EnterpriseMember;
import pl.michal_sobiech.core.user.User;
import pl.michal_sobiech.core.user.UserGroup;

@Getter
@RequiredArgsConstructor
public class Entrepreneur implements EnterpriseMember {

    private final long userId;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final String passwordHash;
    private final String iban;

    public static boolean matchesUser(User user) {
        return user.getUserGroup() == UserGroup.ENTREPRENEUR;
    }

    public static Optional<Entrepreneur> fromUser(User user) {
        if (matchesUser(user)) {
            return Optional.of(new Entrepreneur(
                    user.getUserId(),
                    user.getUsername(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getPasswordHash(),
                    user.getIban().get()));
        } else {
            return Optional.empty();
        }
    }

    public static Entrepreneur fromUserOrThrow(User user) {
        return fromUser(user)
                .orElseThrow(() -> new IllegalArgumentException("User is not an entrepreneur"));
    }
}
