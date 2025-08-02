package pl.michal_sobiech.engineering_thesis.customer;

public record CreateCustomerRequest(

        String firstName,
        String lastName,
        String email,
        String password

) {
}
