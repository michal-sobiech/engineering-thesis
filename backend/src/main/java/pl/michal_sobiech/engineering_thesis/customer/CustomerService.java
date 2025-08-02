package pl.michal_sobiech.engineering_thesis.customer;

import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.independent_end_user.IndependentEndUser;
import pl.michal_sobiech.engineering_thesis.independent_end_user.IndependentEndUserRepository;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final IndependentEndUserRepository independentEndUserRepository;

    @Transactional
    public Customer createCustomer(CreateCustomerRequest dto) {

        IndependentEndUser independentEndUser = IndependentEndUser.builder()
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .email(dto.email())
                .passwordHash("d34db33f")
                .build();
        independentEndUser = independentEndUserRepository.save(independentEndUser);

        Customer customer = Customer.builder()
                .independentEndUser(independentEndUser)
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