package pl.michal_sobiech.engineering_thesis.appointment;

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
    private long appointmentId;

    @Column(name = "enterprise_service_id")
    private long enterpriseServiceId;

    @Nullable
    @Column(name = "customer_user_id", nullable = true)
    private long customerUserId;

    @Column(name = "start_time")
    private OffsetDateTime startTime;

    @Column(name = "end_time")
    private OffsetDateTime endTime;

    @Column(name = "is_custom")
    private boolean isCustom;

    @Nullable
    @Column(name = "is_accepted", nullable = true)
    private boolean isAccepted;

    @Nullable
    @Column(name = "rejection_message", nullable = true)
    private String rejectionMessage;

}
