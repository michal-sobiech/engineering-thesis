package pl.michal_sobiech.engineering_thesis.customer;

public record CustomerDto(

        String firstName,
        String lastName,
        String email

) {

    public static CustomerDto from(Customer customer) {
        String firstName = customer.getIndependentEndUser().getFirstName();
        String lastName = customer.getIndependentEndUser().getLastName();
        String email = customer.getIndependentEndUser().getEmail();
        return new CustomerDto(firstName, lastName, email);
    }

}
