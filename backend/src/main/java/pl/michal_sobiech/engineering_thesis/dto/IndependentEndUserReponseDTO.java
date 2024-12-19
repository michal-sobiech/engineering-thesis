package pl.michal_sobiech.engineering_thesis.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class IndependentEndUserReponseDTO {
    private String firstName;
    private String lastName;
    private String email;
}
