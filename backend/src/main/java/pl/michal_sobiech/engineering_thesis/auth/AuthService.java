package pl.michal_sobiech.engineering_thesis.auth;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.employee.Employee;
import pl.michal_sobiech.engineering_thesis.entrepreneur.Entrepreneur;
import pl.michal_sobiech.engineering_thesis.exceptions.exceptions.ForbiddenException;
import pl.michal_sobiech.engineering_thesis.exceptions.exceptions.UnauthorizedException;
import pl.michal_sobiech.engineering_thesis.independent_end_user.IndependentEndUser;
import pl.michal_sobiech.engineering_thesis.user.User;
import pl.michal_sobiech.engineering_thesis.user.UserEntity;
import pl.michal_sobiech.engineering_thesis.user.UserService;
import pl.michal_sobiech.engineering_thesis.utils.AuthUtils;

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

        UserEntity user = userService.findById(userId).orElseThrow();
        return User.fromEntity(user);
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
            return Entrepreneur.fromUser(user);
        } catch (Exception exception) {
            throw new ForbiddenException(exception);
        }
    }

    public Employee requireEmployee() {
        User user = requireAuthorizedUser();
        return Employee.fromUser(user);
    }
}
