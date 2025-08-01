package pl.michal_sobiech.engineering_thesis.extension;

import pl.michal_sobiech.engineering_thesis.dto.CustomerResponseDTO;
import pl.michal_sobiech.engineering_thesis.model.Customer;

public class CustomerExtension {

    public static CustomerResponseDTO makeCustomerResponseDTO(Customer customer) {
        return CustomerResponseDTO.builder()
                                  .firstName(customer.getFirstName())
                                  .lastName(customer.getLastName())
                                  .email(customer.getEmail())
                                  .build();
    }

}
