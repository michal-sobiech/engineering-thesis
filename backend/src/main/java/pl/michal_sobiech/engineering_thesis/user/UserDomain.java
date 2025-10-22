package pl.michal_sobiech.engineering_thesis.user;

import java.util.Optional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserDomain {

    private final long userId;
    private final UserGroup userGroup;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String passwordHash;
    private final Optional<Long> enterpriseId;

    public static UserDomain fromEntity(User entity) {
        return new UserDomain(
                entity.getUserId(),
                entity.getUserGroup(),
                entity.getUsername(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getPasswordHash(),
                Optional.ofNullable(entity.getEnterpriseId()));
    }

}
