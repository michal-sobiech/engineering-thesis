package pl.michal_sobiech.engineering_thesis.user;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserDomain {

    private final long userId;
    private final UserGroup userGroup;
    private final UsernameNamespace usernameNamespace;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String passwordHash;
    private final Optional<Long> enterpriseId;

    public static UserDomain fromEntity(User entity) {
        return new UserDomain(
                entity.getUserId(),
                entity.getUserGroup(),
                entity.getUsernameNamespace(),
                entity.getUsername(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getPasswordHash(),
                Optional.ofNullable(entity.getEnterpriseId()));
    }

}
