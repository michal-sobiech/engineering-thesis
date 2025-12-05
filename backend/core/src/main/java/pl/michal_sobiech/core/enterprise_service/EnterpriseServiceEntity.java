package pl.michal_sobiech.core.enterprise_service;

import java.math.BigDecimal;
import java.time.ZoneId;

import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.michal_sobiech.core.currency_iso.CurrencyIso;

@Entity
@Table(name = "enterprise_service")
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

    @Column(name = "time_zone")
    private ZoneId timeZone;

    private boolean takesCustomAppointments;

    @Nullable
    @Column(name = "max_distance_km", nullable = true)
    private Double maxDistanceKm;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private EnterpriseServiceCathegory cathegory;

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "currency", columnDefinition = "currency_iso")
    private CurrencyIso currency;

    @Column(name = "suspended_by_admin")
    private boolean suspendedByAdmin;

}
