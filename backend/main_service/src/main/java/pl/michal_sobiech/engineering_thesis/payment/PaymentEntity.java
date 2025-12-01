package pl.michal_sobiech.engineering_thesis.payment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "payment")
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntity {

    @Column(name = "paymentId")
    private Long paymentId;

    @Column(name = "adyen_psp_reference")
    private String adyenPspReference;

}
