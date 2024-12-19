package pl.michal_sobiech.engineering_thesis.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class IndependentEndUserCreateDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
