package pl.michal_sobiech.infra.appointment;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.appointment.AppointmentEntity;
import pl.michal_sobiech.core.appointment.AppointmentRepository;
import pl.michal_sobiech.core.appointment.GetEnterpriseServiceFutureScheduledAppointmentsResponseRow;

@Component
@RequiredArgsConstructor
public class AppointmentRepositoryImpl implements AppointmentRepository {

    private final SpringAppointmentRepository springAppointmentRepository;

    @Override
    public Optional<AppointmentEntity> findById(long appointmentId) {
        return springAppointmentRepository.findById(appointmentId);
    }

    @Override
    public AppointmentEntity save(AppointmentEntity record) {
        return springAppointmentRepository.save(record);
    }

    @Override
    public List<AppointmentEntity> findAllInRange(
            long serviceId,
            OffsetDateTime from,
            OffsetDateTime to) {
        return springAppointmentRepository.findAllInRange(serviceId, from, to);
    }

    @Override
    public List<AppointmentEntity> findConfirmedInDatetimeRange(
            long serviceId,
            OffsetDateTime from,
            OffsetDateTime to) {
        return springAppointmentRepository.findConfirmedInDatetimeRange(serviceId, from, to);
    }

    @Override
    public List<AppointmentEntity> findConfirmedCustomAppointmentsOfCustomer(
            long customerUserId) {
        return springAppointmentRepository.findConfirmedCustomAppointmentsOfCustomer(customerUserId);
    }

    @Override
    public List<AppointmentEntity> findCustomerUncancelledFuturePendingCustomAppointments(
            long customerUserId) {
        return springAppointmentRepository.findCustomerUncancelledFuturePendingCustomAppointments(customerUserId);
    }

    @Override
    public List<AppointmentEntity> findRejectedCustomAppointmentsOfCustomer(
            long customerUserId) {
        return springAppointmentRepository.findRejectedCustomAppointmentsOfCustomer(customerUserId);
    }

    @Override
    public List<AppointmentEntity> findNonCustomAppointmentsOfCustomer(
            long customerUserId) {
        return springAppointmentRepository.findNonCustomAppointmentsOfCustomer(customerUserId);
    }

    @Override
    public List<AppointmentEntity> findNonCustomAppointments(
            Long customerUserId,
            Long enterpriseServiceId,
            Long enterpriseId,
            boolean isCancelled,
            Boolean futureVsPast) {
        return springAppointmentRepository.findNonCustomAppointments(customerUserId, enterpriseServiceId, enterpriseId,
                isCancelled, futureVsPast);
    }

    @Override
    public List<GetEnterpriseServiceFutureScheduledAppointmentsResponseRow> findNonCustomAppointmentsWithDetails(
            Long customerUserId,
            Long enterpriseServiceId,
            Long enterpriseId,
            boolean isCancelled,
            Boolean futureVsPast) {
        return springAppointmentRepository.findNonCustomAppointmentsWithDetails(customerUserId, enterpriseServiceId,
                enterpriseId, isCancelled, futureVsPast);
    }

    // TODO maybe Optionals?
    @Override
    public List<AppointmentEntity> findCustomAppointments(
            Long customerUserId,
            Long enterpriseServiceId,
            Long enterpriseId,
            boolean isCancelled,
            Boolean futureVsPast,
            Boolean acceptedVsRejected) {
        return springAppointmentRepository.findCustomAppointments(customerUserId, enterpriseServiceId, enterpriseId,
                isCancelled, futureVsPast, acceptedVsRejected);
    }

    @Override
    public List<GetEnterpriseServiceFutureScheduledAppointmentsResponseRow> findCustomAppointmentsWithDetails(
            Long customerUserId,
            Long enterpriseServiceId,
            Long enterpriseId,
            boolean isCancelled,
            Boolean futureVsPast,
            Boolean acceptedVsRejected) {
        return springAppointmentRepository.findCustomAppointmentsWithDetails(customerUserId, enterpriseServiceId,
                enterpriseId, isCancelled,
                futureVsPast, acceptedVsRejected);
    }

    @Override
    public List<AppointmentEntity> findPastScheduledAppointmentsWaitingForPayoutProcessing() {
        return springAppointmentRepository.findPastScheduledAppointmentsWaitingForPayoutProcessing();
    }

}
