package pl.michal_sobiech.engineering_thesis.appointment;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pl.michal_sobiech.engineering_thesis.appointment.custom.pending.GetEnterpriseServiceUncancelledFuturePendingAppointmentsResponseRow;
import pl.michal_sobiech.engineering_thesis.appointment.scheduled.GetEnterpriseServiceFutureScheduledAppointmentsResponseRow;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {

    @Query("""
            SELECT appointment
            FROM AppointmentEntity appointment
            WHERE appointment.startTime >= :from
            AND appointment.endTime <= :to
            AND appointment.enterpriseServiceId = :serviceId
            """)
    public List<AppointmentEntity> findAllInRange(
            @Param("serviceId") long serviceId,
            @Param("from") OffsetDateTime from,
            @Param("to") OffsetDateTime to);

    @Query("""
            SELECT appointment
            FROM AppointmentEntity appointment
            WHERE appointment.startTime >= :from
            AND appointment.endTime <= :to
            AND appointment.enterpriseServiceId = :serviceId
            AND appointment.isCustom = true
            AND appointment.isAccepted = true
            """)
    public List<AppointmentEntity> findConfirmedInDatetimeRange(
            @Param("serviceId") long serviceId,
            @Param("from") OffsetDateTime from,
            @Param("to") OffsetDateTime to);

    @Query("""
            SELECT appointment
            FROM AppointmentEntity appointment
            WHERE appointment.customerUserId = :customerUserId
            AND appointment.isCustom = TRUE
            AND appointment.isAccepted = TRUE
            """)
    public List<AppointmentEntity> findConfirmedCustomAppointmentsOfCustomer(
            @Param("customerUserId") long customerUserId);

    @Query("""
            SELECT appointment
            FROM AppointmentEntity appointment
            WHERE appointment.customerUserId = :customerUserId
            AND appointment.cancelled = FALSE
            AND CURRENT_TIMESTAMP < appointment.endTime
            AND appointment.isCustom = TRUE
            AND appointment.isAccepted IS NULL
            """)
    public List<AppointmentEntity> findCustomerUncancelledFuturePendingCustomAppointments(
            @Param("customerUserId") long customerUserId);

    @Query("""
            SELECT appointment
            FROM AppointmentEntity appointment
            WHERE appointment.customerUserId = :customerUserId
            AND appointment.isCustom = TRUE
            AND appointment.isAccepted = FALSE
            """)
    public List<AppointmentEntity> findRejectedCustomAppointmentsOfCustomer(
            @Param("customerUserId") long customerUserId);

    @Query("""
            SELECT appointment
            FROM AppointmentEntity appointment
            WHERE appointment.customerUserId = :customerUserId
            AND appointment.isCustom = FALSE
            """)
    public List<AppointmentEntity> findNonCustomAppointmentsOfCustomer(
            @Param("customerUserId") long customerUserId);

    @Query("""
            SELECT
            appointment.appointmentId as appointmentId,
            user.username as username,
            user.firstName as userFirstName,
            user.lastName as userLastName,
            appointment.address as address,
            appointment.startTime as startGlobalDatetime,
            appointment.endTime as endGlobalDatetime,
            service.timeZone as timezone,
            appointment.price as price,
            appointment.currency as currency
            FROM AppointmentEntity appointment
            JOIN UserEntity user ON user.userId = appointment.customerUserId
            JOIN EnterpriseServiceEntity service ON appointment.enterpriseServiceId = service.enterpriseServiceId
            WHERE appointment.enterpriseServiceId = :enterpriseServiceId
            AND appointment.cancelled = FALSE
            AND appointment.isCustom = TRUE
            AND appointment.isAccepted = TRUE
            AND CURRENT_TIMESTAMP < appointment.endTime
            """)
    public List<GetEnterpriseServiceFutureScheduledAppointmentsResponseRow> getUncancelledFutureScheduledCustomAppointmentsOfEnterpriseService(
            @Param("enterpriseServiceId") long enterpriseServiceId);

    @Query("""
            SELECT
            appointment.appointmentId as appointmentId,
            user.username as username,
            user.firstName as userFirstName,
            user.lastName as userLastName,
            service.address as address,
            appointment.startTime as startGlobalDatetime,
            appointment.endTime as endGlobalDatetime,
            service.timeZone as timezone,
            appointment.price as price,
            appointment.currency as currency
            FROM AppointmentEntity appointment
            JOIN UserEntity user ON user.userId = appointment.customerUserId
            JOIN EnterpriseServiceEntity service ON appointment.enterpriseServiceId = service.enterpriseServiceId
            WHERE appointment.enterpriseServiceId = :enterpriseServiceId
            AND appointment.cancelled = FALSE
            AND appointment.isCustom = FALSE
            AND CURRENT_TIMESTAMP < appointment.endTime
            """)
    public List<GetEnterpriseServiceFutureScheduledAppointmentsResponseRow> getUncancelledFutureScheduledNonCustomAppointmentsOfEnterpriseService(
            @Param("enterpriseServiceId") long enterpriseServiceId);

    // TODO what about cancelled appointments

    @Query("""
            SELECT
            appointment.appointmentId as appointmentId,
            user.username as username,
            user.firstName as userFirstName,
            user.lastName as userLastName,
            appointment.address as address,
            appointment.startTime as startGlobalDatetime,
            appointment.endTime as endGlobalDatetime,
            service.timeZone as timezone,
            appointment.price as price,
            appointment.currency as currency
            FROM AppointmentEntity appointment
            JOIN UserEntity user ON user.userId = appointment.customerUserId
            JOIN EnterpriseServiceEntity service ON appointment.enterpriseServiceId = service.enterpriseServiceId
            WHERE appointment.enterpriseServiceId = :enterpriseServiceId
            AND appointment.cancelled = FALSE
            AND appointment.isCustom = TRUE
            AND appointment.isAccepted IS NULL
            AND CURRENT_TIMESTAMP < appointment.endTime
            """)
    public List<GetEnterpriseServiceUncancelledFuturePendingAppointmentsResponseRow> findEnterpriseServiceUncancelledFuturePendingAppointments(
            @Param("enterpriseServiceId") long enterpriseServiceId);

    @Query("""
            SELECT appointment
            FROM AppointmentEntity appointment
            JOIN UserEntity user ON user.userId = appointment.customerUserId
            JOIN EnterpriseServiceEntity service ON appointment.enterpriseServiceId = service.enterpriseServiceId
            JOIN EnterpriseEntity enterprise ON enterprise.enterpriseId = service.enterpriseId
            WHERE appointment.isCustom = TRUE
            AND appointment.cancelled = :isCancelled
            AND (:customerUserId IS NULL OR appointment.customerUserId = :customerUserId )
            AND (:enterpriseServiceId IS NULL OR service.enterpriseServiceId = :enterpriseServiceId)
            AND (:enterpriseId IS NULL OR enterprise.enterpriseId = :enterpriseId)
            AND (
                (:isAccepted IS NULL AND appointment.isAccepted IS NULL)
                OR
                (:isAccepted IS NOT NULL AND appointment.isAccepted = :isAccepted)
            )
            AND (
                (:isFutureAccepted = TRUE AND CURRENT_TIMESTAMP < appointment.endTime)
                OR
                (:isPastAccepted = TRUE AND CURRENT_TIMESTAMP > appointment.endTime)
            )
            """)
    public List<AppointmentEntity> findCustomAppointments(
            @Param("customerUserId") Long customerUserId,
            @Param("enterpriseServiceId") Long enterpriseServiceId,
            @Param("enterpriseId") Long enterpriseId,
            @Param("isCancelled") boolean isCancelled,
            @Param("futureVsPast") Boolean futureVsPast,
            @Param("isAccepted") Boolean acceptedVsRejected);

}
