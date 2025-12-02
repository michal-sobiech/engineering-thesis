package pl.michal_sobiech.shared.enterprise_member;

import pl.michal_sobiech.engineering_thesis.employee.Employee;
import pl.michal_sobiech.shared.entrepreneur.Entrepreneur;
import pl.michal_sobiech.shared.user.User;

public class EnterpriseMemberFactory {

    public static EnterpriseMember fromUser(User user) {
        try {
            return Entrepreneur.fromUser(user);
        } catch (Exception exception) {
        }

        try {
            return Employee.fromUser(user);
        } catch (Exception exception) {
        }

        throw new IllegalArgumentException("User is not enterprise staff");
    }

}
