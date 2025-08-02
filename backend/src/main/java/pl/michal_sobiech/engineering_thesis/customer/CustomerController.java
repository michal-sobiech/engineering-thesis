package pl.michal_sobiech.engineering_thesis.customer;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/create_customer")
    public CustomerDto createCustomer(@RequestBody CreateCustomerRequest request) {
        Customer customer = customerService.createCustomer(request);
        return CustomerDto.from(customer);
    }

    @GetMapping("/get_customer")
    public ResponseEntity<CustomerDto> getCustomer(@RequestParam long id) {
        Optional<Customer> customer = customerService.getCustomer(id);

        if (customer.isPresent()) {
            CustomerDto dto = CustomerDto.from(customer.get());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }

    }
}
