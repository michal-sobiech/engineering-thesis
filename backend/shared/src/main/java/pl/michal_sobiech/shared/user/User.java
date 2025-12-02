package pl.michal_sobiech.shared.user;

import java.util.Optional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class User {

    private final long userId;
    private final UserGroup userGroup;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String passwordHash;
    private final Optional<Long> enterpriseId;

    public static User fromEntity(UserEntity entity) {
        return new User(
                entity.getUserId(),
                entity.getUserGroup(),
                entity.getUsername(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getPasswordHash(),
                Optional.ofNullable(entity.getEnterpriseId()));
    }

}
