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

@Entity(name = "enterprise_service")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnterpriseServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enterprise_service_id")
    private long enterpriseServiceId;

    @Column(name = "enterprise_id")
    private long enterpriseId;

    private String name;
    private String description;
    private String address;
    private double latitude;
    private double longitude;

    private ZoneId timeZone;

    private boolean takesCustomAppointments;
    @Column(name = "max_distance_km", nullable = true)
    private double maxDistanceKm;

    private BigDecimal price;
    private Currency currency;

    @Column(name = "enterprise_service_cathegory")
    private EnterpriseServiceCathegory enterpriseServiceCathegory;

}
