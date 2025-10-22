package pl.michal_sobiech.engineering_thesis.auth;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.employee.EmployeeDomain;
import pl.michal_sobiech.engineering_thesis.entrepreneur.Entrepreneur;
import pl.michal_sobiech.engineering_thesis.exceptions.exceptions.ForbiddenException;
import pl.michal_sobiech.engineering_thesis.exceptions.exceptions.UnauthorizedException;
import pl.michal_sobiech.engineering_thesis.independent_end_user.IndependentEndUserDomain;
import pl.michal_sobiech.engineering_thesis.user.User;
import pl.michal_sobiech.engineering_thesis.user.UserDomain;
import pl.michal_sobiech.engineering_thesis.user.UserService;
import pl.michal_sobiech.engineering_thesis.utils.AuthUtils;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;

    public UserDomain requireAuthorizedUser() {
        Optional<Long> optionalUserId = AuthUtils.getAuthPrincipal();
        if (optionalUserId.isEmpty()) {
            throw new UnauthorizedException();
        }
        long userId = optionalUserId.get();

        User user = userService.findById(userId).orElseThrow();
        return UserDomain.fromEntity(user);
    }

    public IndependentEndUserDomain requireIndependentEndUser() {
        UserDomain user = requireAuthorizedUser();
        try {
            return IndependentEndUserDomain.fromUserDomain(user);
        } catch (Exception exception) {
            throw new ForbiddenException(exception);
        }
    }

    public Entrepreneur requireEntrepreneur() {
        UserDomain user = requireAuthorizedUser();
        try {
            return Entrepreneur.fromUserDomain(user);
        } catch (Exception exception) {
            throw new ForbiddenException(exception);
        }
    }

    public EmployeeDomain requireEmployee() {
        UserDomain user = requireAuthorizedUser();
        return EmployeeDomain.fromUserDomain(user);
    }
}
