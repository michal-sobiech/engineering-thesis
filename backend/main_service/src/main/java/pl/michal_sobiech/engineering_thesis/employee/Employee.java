package pl.michal_sobiech.engineering_thesis.employee;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.shared.enterprise_member.EnterpriseMember;
import pl.michal_sobiech.shared.user.User;
import pl.michal_sobiech.shared.user.UserGroup;

@Getter
@RequiredArgsConstructor
public class Employee implements EnterpriseMember {

    private final long userId;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String passwordHash;
    private final long enterpriseId;

    public static Employee fromUser(User user) {
        if (user.getUserGroup() != UserGroup.EMPLOYEE) {
            throw new IllegalArgumentException("User is not an employee");
        }

        long enterpriseId;
        if (user.getEnterpriseId().isEmpty()) {
            throw new IllegalArgumentException("Enterprise id cannot be empty");
        }
        enterpriseId = user.getEnterpriseId().get();

        return new Employee(
                user.getUserId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getPasswordHash(),
                enterpriseId);
    }
}
