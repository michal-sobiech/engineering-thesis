package pl.michal_sobiech.engineering_thesis.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.michal_sobiech.engineering_thesis.dto.CustomerCreateDTO;
import pl.michal_sobiech.engineering_thesis.model.Customer;
import pl.michal_sobiech.engineering_thesis.repository.CustomerRepository;
import pl.michal_sobiech.engineering_thesis.repository.IndependentEndUserRepository;

@Service
public class CustomerService {
    
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    IndependentEndUserRepository independentEndUserRepository;

    @Transactional
    public Customer createCustomer(CustomerCreateDTO dto) {
        Customer customer = Customer.builder()
                                    .firstName(dto.getFirstName())
                                    .lastName(dto.getLastName())
                                    .email(dto.getEmail())
                                    .passwordHash("d34db33f")
                                    .build();
        customer = customerRepository.save(customer);
        return customer;
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "customers", key = "#id")
    public Optional<Customer> getCustomer(long id) {
        return customerRepository.findById(id);
    }
}