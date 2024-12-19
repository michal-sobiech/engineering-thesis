package pl.michal_sobiech.engineering_thesis.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class CustomerCreateDTO extends IndependentEndUserCreateDTO {
//    public CustomerCreateDTO(String firstName, String lastName, String email, String password) {
//        super(firstName, lastName, email, password);
//    }
}
