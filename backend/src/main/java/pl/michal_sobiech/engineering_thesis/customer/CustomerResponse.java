package pl.michal_sobiech.engineering_thesis.customer;

public record CustomerResponse(

        String firstName,
        String lastName,
        String email

) {

    public static CustomerResponse from(Customer customer) {
        String firstName = customer.getIndependentEndUserId().getFirstName();
        String lastName = customer.getIndependentEndUserId().getLastName();
        String email = customer.getIndependentEndUserId().getEmail();
        return new CustomerResponse(firstName, lastName, email);
    }

}
