package pl.michal_sobiech.engineering_thesis.independent_end_user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IndependentEndUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "independent_end_user_id")
    private long independentEndUserId;

    @Column(name = "user_id", insertable = false)
    private long userId;

    private String firstName;
    private String lastName;
    private String email;
    private String passwordHash;

}