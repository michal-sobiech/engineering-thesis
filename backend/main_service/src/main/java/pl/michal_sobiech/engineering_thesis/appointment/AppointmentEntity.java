package pl.michal_sobiech.engineering_thesis.appointment;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

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
import lombok.ToString;
import pl.michal_sobiech.engineering_thesis.currency_iso.CurrencyIso;

@Entity
@Table(name = "appointment")
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Long appointmentId;

    @Column(name = "enterprise_service_id")
    private Long enterpriseServiceId;

    @Column(name = "customer_user_id")
    private long customerUserId;

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "currency", columnDefinition = "currency_iso")
    private CurrencyIso currency;

    @Column(name = "start_time")
    private OffsetDateTime startTime;

    @Column(name = "end_time")
    private OffsetDateTime endTime;

    @Column(name = "is_custom")
    private boolean isCustom;

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

    @Column(name = "is_paid")
    private boolean isPaid;

    @Nullable
    @Column(name = "payment_id", nullable = true)
    private Long paymentId;

    @Column(name = "was_payout_processed")
    private boolean wasPayoutProcessed;

}
