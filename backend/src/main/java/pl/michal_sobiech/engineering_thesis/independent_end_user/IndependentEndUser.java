package pl.michal_sobiech.engineering_thesis.independent_end_user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class IndependentEndUser {

    @Id
    @Column(name = "independent_end_user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final long id;

    private final String firstName;
    private final String lastName;
    private final String email;
    private final String passwordHash;

}