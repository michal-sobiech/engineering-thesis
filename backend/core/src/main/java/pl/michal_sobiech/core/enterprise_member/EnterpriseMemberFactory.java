package pl.michal_sobiech.core.enterprise_member;

import pl.michal_sobiech.core.employee.Employee;
import pl.michal_sobiech.core.entrepreneur.Entrepreneur;
import pl.michal_sobiech.core.user.User;

public class EnterpriseMemberFactory {

    public static EnterpriseMember fromUser(User user) {
        try {
            return Entrepreneur.fromUserOrThrow(user);
        } catch (Exception exception) {
        }

        try {
            return Employee.fromUser(user);
        } catch (Exception exception) {
        }

        throw new IllegalArgumentException("User is not enterprise staff");
    }

}
