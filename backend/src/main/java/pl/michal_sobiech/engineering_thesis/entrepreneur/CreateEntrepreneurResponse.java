package pl.michal_sobiech.engineering_thesis.entrepreneur;

public record CreateEntrepreneurResponse(

        String firstName,
        String lastName,
        String email

) {

    public static CreateEntrepreneurResponse from(Entrepreneur entrepreneur) {
        String firstName = entrepreneur.getIndependentEndUserId().getFirstName();
        String lastName = entrepreneur.getIndependentEndUserId().getLastName();
        String email = entrepreneur.getIndependentEndUserId().getEmail();
        return new CreateEntrepreneurResponse(firstName, lastName, email);
    }

}