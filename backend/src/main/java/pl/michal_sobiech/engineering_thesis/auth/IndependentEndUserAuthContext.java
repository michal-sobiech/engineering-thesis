package pl.michal_sobiech.engineering_thesis.auth;

import pl.michal_sobiech.engineering_thesis.independent_end_user.IndependentEndUser;

public record IndependentEndUserAuthContext(

        long userId,
        IndependentEndUser independentEndUser

) {

}
