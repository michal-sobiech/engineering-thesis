package pl.michal_sobiech.engineering_thesis.entrepreneur;

public record CreateEntrepreneurRequest(

                String firstName,

                String lastName,

                String email,

                String passwordHash

) {

}
