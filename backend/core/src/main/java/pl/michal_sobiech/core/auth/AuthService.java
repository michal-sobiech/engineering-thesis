package pl.michal_sobiech.core.auth;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.admin.Admin;
import pl.michal_sobiech.core.customer.Customer;
import pl.michal_sobiech.core.employee.Employee;
import pl.michal_sobiech.core.enterprise_member.EnterpriseMember;
import pl.michal_sobiech.core.enterprise_member.EnterpriseMemberFactory;
import pl.michal_sobiech.core.entrepreneur.Entrepreneur;
import pl.michal_sobiech.core.exceptions.ForbiddenException;
import pl.michal_sobiech.core.exceptions.UnauthorizedException;
import pl.michal_sobiech.core.head_admin.HeadAdmin;
import pl.michal_sobiech.core.independent_end_user.IndependentEndUser;
import pl.michal_sobiech.core.user.User;
import pl.michal_sobiech.core.user.UserService;
import pl.michal_sobiech.core.utils.AuthUtils;

@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;

    public User requireAuthorizedUser() {
        Optional<Long> optionalUserId = AuthUtils.getAuthPrincipal();
        if (optionalUserId.isEmpty()) {
            throw new UnauthorizedException();
        }
        long userId = optionalUserId.get();

        return userService.findById(userId).orElseThrow();
    }

    public IndependentEndUser requireIndependentEndUser() {
        User user = requireAuthorizedUser();
        try {
            return IndependentEndUser.fromUser(user);
        } catch (Exception exception) {
            throw new ForbiddenException(exception);
        }
    }

    public Entrepreneur requireEntrepreneur() {
        User user = requireAuthorizedUser();
        try {
            return Entrepreneur.fromUserOrThrow(user);
        } catch (Exception exception) {
            throw new ForbiddenException(exception);
        }
    }

    public Employee requireEmployee() {
        User user = requireAuthorizedUser();
        try {
            return Employee.fromUser(user);
        } catch (Exception exception) {
            throw new ForbiddenException();
        }
    }

    public EnterpriseMember requireEnterpriseMember() {
        User user = requireAuthorizedUser();
        try {
            return EnterpriseMemberFactory.fromUser(user);
        } catch (Exception exception) {
            throw new ForbiddenException();
        }
    }

    public Customer requireCustomer() {
        User user = requireAuthorizedUser();
        return Customer.fromUser(user);
    }

    public Admin requireAdmin() {
        User user = requireAuthorizedUser();
        return Admin.fromUser(user);
    }

    public HeadAdmin requireHeadAdmin() {
        User user = requireAuthorizedUser();
        return HeadAdmin.fromUser(user);
    }

}
