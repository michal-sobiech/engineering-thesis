package pl.michal_sobiech.engineering_thesis.customer;

import org.SwaggerCodeGenExample.api.CustomersApi;
import org.SwaggerCodeGenExample.model.CreateIndependentEndUserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CustomerController implements CustomersApi {

    private final CustomerService customerService;

    public ResponseEntity<Void> createCustomer(
            CreateIndependentEndUserRequest createIndependentEndUserRequest) {
        customerService.save(createIndependentEndUserRequest);
        return ResponseEntity.ok().build();
    }

}
