package pl.michal_sobiech.engineering_thesis.appointment;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.michal_sobiech.engineering_thesis.currency_iso.CurrencyIso;

@Entity
@Table(name = "appointment")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Long appointmentId;

    @Column(name = "enterprise_service_id")
    private Long enterpriseServiceId;

    @Nullable
    @Column(name = "customer_user_id", nullable = true)
    private Long customerUserId;

    private BigDecimal price;

    private CurrencyIso currency;

    @Column(name = "start_time")
    private OffsetDateTime startTime;

    @Column(name = "end_time")
    private OffsetDateTime endTime;

    @Column(name = "is_custom")
    private Boolean isCustom;

    @Nullable
    @Column(name = "is_accepted", nullable = true)
    private Boolean isAccepted;

    @Nullable
    @Column(name = "rejection_message", nullable = true)
    private String rejectionMessage;

    @Nullable
    @Column(nullable = true)
    private String address;

    @Nullable
    @Column(nullable = true)
    private Double longitude;

    @Nullable
    @Column(nullable = true)
    private Double latitude;

    private boolean cancelled;

}
