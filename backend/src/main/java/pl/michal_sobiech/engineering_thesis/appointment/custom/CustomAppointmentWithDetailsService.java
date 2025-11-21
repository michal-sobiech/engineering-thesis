package pl.michal_sobiech.engineering_thesis.appointment.custom;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.appointment.query.AppointmentQueryTimeRange;
import pl.michal_sobiech.engineering_thesis.appointment.query.CustomAppointmentStatus;
import pl.michal_sobiech.engineering_thesis.customer.Customer;
import pl.michal_sobiech.engineering_thesis.customer.CustomerService;

@Service
@RequiredArgsConstructor
public class CustomAppointmentWithDetailsService {

    private final CustomAppointmentQueryService customAppointmentQueryService;
    private final CustomerService customerService;

    public List<CustomAppointmentWithDetails> getEnterpriseServiceUncancelledFutureScheduledCustomAppointmentsWithDetails(
            long enterpriseServiceId) {
        return customAppointmentQueryService.getCustomAppointments(
                Optional.empty(),
                Optional.of(enterpriseServiceId),
                Optional.empty(),
                Optional.of(false),
                Optional.of(AppointmentQueryTimeRange.FUTURE),
                CustomAppointmentStatus.ACCEPTED)
                .stream()
                .map(UncancelledPendingAppointment::fromEntity)
                .map(Optional::orElseThrow)
                .map(appointment -> {
                    Customer customer = customerService.getByUserId(appointment.customerUserId()).orElseThrow();
                    return new UncancelledPendingAppointmentWithDetails(
                            appointment.appointmentId(),
                            appointment.enterpriseServiceId(),
                            appointment.customerUserId(),
                            customer.getEmail(),
                            customer.getFirstName(),
                            customer.getLastName(),
                            appointment.price(),
                            appointment.currency(),
                            appointment.startInstant(),
                            appointment.endInstant(),
                            appointment.location());
                })
                .collect(Collectors.toList());
    }

    public List<CustomAppointmentWithDetails> getEnterpriseServiceUncancelledFuturePendingCustomAppointmentsWithDetails(
            long enterpriseServiceId) {
        return customAppointmentQueryService.getCustomAppointments(
                Optional.empty(),
                Optional.of(enterpriseServiceId),
                Optional.empty(),
                Optional.empty(),
                Optional.of(AppointmentQueryTimeRange.FUTURE),
                CustomAppointmentStatus.REJECTED)
                .stream()
                .map(RejectedAppointment::fromEntity)
                .map(Optional::orElseThrow)
                .map(appointment -> {
                    Customer customer = customerService.getByUserId(appointment.customerUserId()).orElseThrow();
                    return new RejectedAppointmentWithDetails(
                            appointment.appointmentId(),
                            appointment.enterpriseServiceId(),
                            appointment.customerUserId(),
                            customer.getEmail(),
                            customer.getFirstName(),
                            customer.getLastName(),
                            appointment.price(),
                            appointment.currency(),
                            appointment.startInstant(),
                            appointment.endInstant(),
                            appointment.location(),
                            appointment.rejectionMessage());
                })
                .collect(Collectors.toList());
    }

}
