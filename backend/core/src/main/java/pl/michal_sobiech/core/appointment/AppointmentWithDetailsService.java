package pl.michal_sobiech.core.appointment;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.customer.Customer;
import pl.michal_sobiech.core.customer.CustomerService;

@RequiredArgsConstructor
public class AppointmentWithDetailsService {

    private final AppointmentService appointmentService;
    private final CustomerService customerService;

    public List<ScheduledAppointmentWithDetails> getEnterpriseServiceUncancelledFutureScheduledAppointmentsWithDetails(
            long enterpriseServiceId) {
        return appointmentService.getServiceUncancelledFutureScheduledAppointments(enterpriseServiceId)
                .stream()
                .map(appointment -> {
                    Customer customer = customerService.getByUserId(appointment.customerUserId()).orElseThrow();
                    return new ScheduledAppointmentWithDetails(
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
                            appointment.paymentStatus());
                })
                .collect(Collectors.toList());
    }

}
