package pl.michal_sobiech.engineering_thesis.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Entrepreneur {

    @Id
    @Column(name = "entrepreneur_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "independent_end_user_id")
    private final IndependentEndUser independentEndUser;

}
