package pl.michal_sobiech.engineering_thesis.dto.create_entrepreneur;

public record CreateEntrepreneurRequest(

        String firstName,

        String lastName,

        String email,

        String passwordHash

) {

}
