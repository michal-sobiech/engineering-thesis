package pl.michal_sobiech.engineering_thesis.customer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class Customer {

    @Column(name = "customer_id")
    private final long customerId;

    @JoinColumn(name = "independent_end_user_id")
    private final long independentEndUserId;

}
