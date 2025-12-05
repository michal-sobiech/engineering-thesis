package pl.michal_sobiech.core.appointment;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository {

    public Optional<AppointmentEntity> findById(long appointmentId);

    public AppointmentEntity save(AppointmentEntity record);

    public List<AppointmentEntity> findAllInRange(
            long serviceId,
            OffsetDateTime from,
            OffsetDateTime to);

    public List<AppointmentEntity> findConfirmedInDatetimeRange(
            long serviceId,
            OffsetDateTime from,
            OffsetDateTime to);

    public List<AppointmentEntity> findConfirmedCustomAppointmentsOfCustomer(
            long customerUserId);

    public List<AppointmentEntity> findCustomerUncancelledFuturePendingCustomAppointments(
            long customerUserId);

    public List<AppointmentEntity> findRejectedCustomAppointmentsOfCustomer(
            long customerUserId);

    public List<AppointmentEntity> findNonCustomAppointmentsOfCustomer(
            long customerUserId);

    public List<AppointmentEntity> findNonCustomAppointments(
            Long customerUserId,
            Long enterpriseServiceId,
            Long enterpriseId,
            boolean isCancelled,
            Boolean futureVsPast);

    public List<GetEnterpriseServiceFutureScheduledAppointmentsResponseRow> findNonCustomAppointmentsWithDetails(
            Long customerUserId,
            Long enterpriseServiceId,
            Long enterpriseId,
            boolean isCancelled,
            Boolean futureVsPast);

    // TODO maybe Optionals?
    public List<AppointmentEntity> findCustomAppointments(
            Long customerUserId,
            Long enterpriseServiceId,
            Long enterpriseId,
            boolean isCancelled,
            Boolean futureVsPast,
            Boolean acceptedVsRejected);

    public List<GetEnterpriseServiceFutureScheduledAppointmentsResponseRow> findCustomAppointmentsWithDetails(
            Long customerUserId,
            Long enterpriseServiceId,
            Long enterpriseId,
            boolean isCancelled,
            Boolean futureVsPast,
            Boolean acceptedVsRejected);

    public List<AppointmentEntity> findPastScheduledAppointmentsWaitingForPayoutProcessing();
}
