package pl.michal_sobiech.engineering_thesis.independent_end_user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class IndependentEndUser {

    @Column(name = "user_id")
    private final long userId;

    @Column(name = "indpendent_end_user_id")
    private final long independentEndUserId;

    private final String firstName;
    private final String lastName;
    private final String email;
    private final String passwordHash;

}