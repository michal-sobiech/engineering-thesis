package pl.michal_sobiech.engineering_thesis.customer;

import org.SwaggerCodeGenExample.api.CustomersApi;
import org.SwaggerCodeGenExample.model.CreateIndependentEndUserRequest;
import org.SwaggerCodeGenExample.model.IndependentEndUserGetMeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.auth.AuthService;

@RestController
@RequiredArgsConstructor
public class CustomerController implements CustomersApi {

    private final CustomerService customerService;
    private final AuthService authService;

    public ResponseEntity<Void> createCustomer(
            CreateIndependentEndUserRequest createIndependentEndUserRequest) {
        customerService.save(createIndependentEndUserRequest);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<IndependentEndUserGetMeResponse> getMeCustomer() {
        Customer customer = authService.requireCustomer();
        var body = new IndependentEndUserGetMeResponse(
                customer.getUserId(),
                customer.getEmail(),
                customer.getFirstName(),
                customer.getLastName());
        return ResponseEntity.ok(body);
    }

}
