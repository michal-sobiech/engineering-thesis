package pl.michal_sobiech.engineering_thesis.auth;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.exceptions.exceptions.ForbiddenException;
import pl.michal_sobiech.engineering_thesis.exceptions.exceptions.UnauthorizedException;
import pl.michal_sobiech.engineering_thesis.user.User;
import pl.michal_sobiech.engineering_thesis.user.UserDomain;
import pl.michal_sobiech.engineering_thesis.user.UserGroup;
import pl.michal_sobiech.engineering_thesis.user.UserService;
import pl.michal_sobiech.engineering_thesis.user.UsernameNamespace;
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

    public UserDomain requireIndependentEndUser() {
        UserDomain user = requireAuthorizedUser();
        if (user.getUsernameNamespace() != UsernameNamespace.EMAIL) {
            throw new ForbiddenException();
        }
        return user;
    }

    public UserDomain requireEntrepreneur() {
        UserDomain user = requireAuthorizedUser();
        if (user.getUserGroup() != UserGroup.ENTREPRENEUR) {
            throw new ForbiddenException();
        }
        return user;
    }

    public UserDomain requireEmployee() {
        UserDomain user = requireAuthorizedUser();
        if (user.getEnterpriseId().isPresent()) {

        }
    }
}
