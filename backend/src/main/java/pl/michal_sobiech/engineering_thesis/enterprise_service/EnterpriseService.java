package pl.michal_sobiech.engineering_thesis.enterprise_service;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.Currency;

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
public class EnterpriseService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enterprise_service_id")
    private long enterpriseServiceId;

    @Column(name = "enterprise_id")
    private long enterpriseId;

    private String name;
    private String description;

    @Column(nullable = true)
    private String address;

    @Column(nullable = true)
    private double latitude;

    @Column(nullable = true)
    private double longitude;

    private ZoneId timeZone;
    private boolean takesCustomAppointments;
    private BigDecimal price;
    private Currency currency;

}
