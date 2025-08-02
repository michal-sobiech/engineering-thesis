package pl.michal_sobiech.engineering_thesis.customer;

public record CustomerResponse(

        String firstName,
        String lastName,
        String email

) {

    public static CustomerResponse from(Customer customer) {
        String firstName = customer.getIndependentEndUser().getFirstName();
        String lastName = customer.getIndependentEndUser().getLastName();
        String email = customer.getIndependentEndUser().getEmail();
        return new CustomerResponse(firstName, lastName, email);
    }

}
