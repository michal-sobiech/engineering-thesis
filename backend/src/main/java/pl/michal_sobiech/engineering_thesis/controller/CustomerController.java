package pl.michal_sobiech.engineering_thesis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import pl.michal_sobiech.engineering_thesis.dto.CustomerCreateDTO;
import pl.michal_sobiech.engineering_thesis.dto.CustomerResponseDTO;
import pl.michal_sobiech.engineering_thesis.extension.CustomerExtension;
import pl.michal_sobiech.engineering_thesis.model.Customer;
import pl.michal_sobiech.engineering_thesis.service.CustomerService;

@RestController
public class CustomerController {
    
    @Autowired
    CustomerService customerService;

    @PostMapping("/create_customer")
    public CustomerResponseDTO createCustomer(@RequestBody CustomerCreateDTO customerDTO) {
        Customer customer = customerService.createCustomer(customerDTO);
        return CustomerExtension.makeCustomerResponseDTO(customer);

    }
}
