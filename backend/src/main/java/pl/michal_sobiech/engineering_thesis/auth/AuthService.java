package pl.michal_sobiech.engineering_thesis.auth;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.entrepreneur.Entrepreneur;
import pl.michal_sobiech.engineering_thesis.entrepreneur.EntrepreneurService;
import pl.michal_sobiech.engineering_thesis.independent_end_user.IndependentEndUser;
import pl.michal_sobiech.engineering_thesis.independent_end_user.IndependentEndUserService;
import pl.michal_sobiech.engineering_thesis.utils.AuthUtils;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final IndependentEndUserService independentEndUserService;
    private final EntrepreneurService entrepreneurService;

    public long requireAuthorizedUser() {
        Optional<Long> optionalUserId = AuthUtils.getAuthPrincipal();
        if (optionalUserId.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return optionalUserId.get();
    }

    public IndependentEndUserAuthContext requireIndependentEndUser() {
        long userId = requireAuthorizedUser();

        Optional<IndependentEndUser> optionalIndependentEndUser = independentEndUserService.findByUserId(userId);
        if (optionalIndependentEndUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        IndependentEndUser independentEndUser = optionalIndependentEndUser.get();

        return new IndependentEndUserAuthContext(userId, independentEndUser);
    }

    public EntrepreneurAuthContext requireEntrepreneur() {
        IndependentEndUserAuthContext independentEndUserAuthContext = requireIndependentEndUser();
        long userId = independentEndUserAuthContext.userId();
        IndependentEndUser independentEndUser = independentEndUserAuthContext.independentEndUser();

        long independentEndUserId = independentEndUser.getIndependentEndUserId();
        Optional<Entrepreneur> optionalEntrepreneur = entrepreneurService
                .findByIndependentEndUserId(independentEndUserId);
        if (optionalEntrepreneur.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        Entrepreneur entrepreneur = optionalEntrepreneur.get();

        return new EntrepreneurAuthContext(userId, independentEndUser, entrepreneur);
    }
}
