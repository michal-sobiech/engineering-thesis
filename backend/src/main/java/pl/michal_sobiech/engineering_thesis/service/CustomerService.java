package pl.michal_sobiech.engineering_thesis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.michal_sobiech.engineering_thesis.dto.CustomerCreateDTO;
import pl.michal_sobiech.engineering_thesis.dto.CustomerResponseDTO;
import pl.michal_sobiech.engineering_thesis.extension.CustomerExtension;
import pl.michal_sobiech.engineering_thesis.model.Customer;
import pl.michal_sobiech.engineering_thesis.repository.CustomerRepository;
import pl.michal_sobiech.engineering_thesis.repository.IndependentEndUserRepository;

@Service
public class CustomerService {
    
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    IndependentEndUserRepository independentEndUserRepository;

    public Customer createCustomer(CustomerCreateDTO dto) {
        Customer customer = Customer.builder()
                                    .firstName(dto.getFirstName())
                                    .lastName(dto.getLastName())
                                    .email(dto.getEmail())
                                    .passwordHash("d34db33f")
                                    .build();
        return customerRepository.save(customer);
    }
}