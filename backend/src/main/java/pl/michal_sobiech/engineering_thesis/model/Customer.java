package pl.michal_sobiech.engineering_thesis.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
public class Customer extends IndependentEndUser {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Builder.Default
    private Long id = null;

    private Long independentUserId;
    private Long paymentMethodId;
}
