package pl.michal_sobiech.engineering_thesis.entrepreneur;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Data;
import pl.michal_sobiech.engineering_thesis.model.IndependentEndUser;

@Entity
@Data
@Builder
public class Entrepreneur {

    @Id
    @Column(name = "entrepreneur_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "independent_end_user_id")
    private final IndependentEndUser independentEndUser;

}
