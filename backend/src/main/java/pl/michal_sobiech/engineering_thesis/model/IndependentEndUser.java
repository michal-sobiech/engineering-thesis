package pl.michal_sobiech.engineering_thesis.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Entity
public class IndependentEndUser implements Serializable {

    @Id
    @Column(name = "independent_end_user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final long id;

    private final String firstName;
    private final String lastName;
    private final String email;
    private final String passwordHash;

    @Column(insertable = false)
    private final long userId;
}