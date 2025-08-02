package pl.michal_sobiech.engineering_thesis.independent_end_user;

public record CreateIndependentEndUserRequest(

        String firstName,
        String lastName,
        String email,
        String password

) {
}
