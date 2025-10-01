package pl.michal_sobiech.engineering_thesis.independent_end_user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class IndependentEndUser {

    @Id
    @Column(name = "independent_end_user_id")
    private final long independentEndUserId;

    @Column(name = "user_id")
    private final long userId;

    private final String firstName;
    private final String lastName;
    private final String email;
    private final String passwordHash;

}