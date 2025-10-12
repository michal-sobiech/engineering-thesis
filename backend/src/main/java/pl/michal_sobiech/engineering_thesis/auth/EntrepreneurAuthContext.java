package pl.michal_sobiech.engineering_thesis.auth;

import pl.michal_sobiech.engineering_thesis.entrepreneur.Entrepreneur;
import pl.michal_sobiech.engineering_thesis.independent_end_user.IndependentEndUser;

public record EntrepreneurAuthContext(

        long userId,
        IndependentEndUser independentEndUser,
        Entrepreneur entrepreneur

) {

}
