package pl.michal_sobiech.engineering_thesis.entrepreneur;

public record CreateEntrepreneurResponse(

        String firstName,
        String lastName,
        String email

) {

    public static CreateEntrepreneurResponse from(Entrepreneur entrepreneur) {
        String firstName = entrepreneur.getIndependentEndUser().getFirstName();
        String lastName = entrepreneur.getIndependentEndUser().getLastName();
        String email = entrepreneur.getIndependentEndUser().getEmail();
        return new CreateEntrepreneurResponse(firstName, lastName, email);
    }

}