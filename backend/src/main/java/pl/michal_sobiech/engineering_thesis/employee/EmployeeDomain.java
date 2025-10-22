package pl.michal_sobiech.engineering_thesis.employee;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.user.UserDomain;
import pl.michal_sobiech.engineering_thesis.user.UserGroup;

@Getter
@RequiredArgsConstructor
public class EmployeeDomain {

    private final long userId;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String passwordHash;
    private final long enterpriseId;

    public static EmployeeDomain fromUserDomain(UserDomain userDomain) {
        if (userDomain.getUserGroup() != UserGroup.EMPLOYEE) {
            throw new IllegalArgumentException("User is not an employee");
        }

        long enterpriseId;
        if (userDomain.getEnterpriseId().isEmpty()) {
            throw new IllegalArgumentException("Enterprise id cannot be empty");
        }
        enterpriseId = userDomain.getEnterpriseId().get();

        return new EmployeeDomain(
                userDomain.getUserId(),
                userDomain.getUsername(),
                userDomain.getFirstName(),
                userDomain.getLastName(),
                userDomain.getPasswordHash(),
                enterpriseId);
    }

}
