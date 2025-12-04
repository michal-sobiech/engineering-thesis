package pl.michal_sobiech.engineering_thesis.auth;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.admin.Admin;
import pl.michal_sobiech.engineering_thesis.customer.Customer;
import pl.michal_sobiech.engineering_thesis.employee.Employee;
import pl.michal_sobiech.engineering_thesis.exceptions.exceptions.ForbiddenException;
import pl.michal_sobiech.engineering_thesis.exceptions.exceptions.UnauthorizedException;
import pl.michal_sobiech.engineering_thesis.head_admin.HeadAdmin;
import pl.michal_sobiech.engineering_thesis.independent_end_user.IndependentEndUser;
import pl.michal_sobiech.engineering_thesis.utils.AuthUtils;
import pl.michal_sobiech.shared.enterprise_member.EnterpriseMember;
import pl.michal_sobiech.shared.enterprise_member.EnterpriseMemberFactory;
import pl.michal_sobiech.shared.entrepreneur.Entrepreneur;
import pl.michal_sobiech.shared.user.User;
import pl.michal_sobiech.shared.user.UserService;

@Service
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
