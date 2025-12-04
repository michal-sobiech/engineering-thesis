package pl.michal_sobiech.shared.appointment;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.customer.Customer;
import pl.michal_sobiech.engineering_thesis.customer.CustomerService;

@Service
@RequiredArgsConstructor
public class AppointmentWithDetailsService {

    private final AppointmentService appointmentService;
    private final CustomerService customerService;

    public List<ScheduledAppointmentWithDetails> getEnterpriseServiceUncancelledFutureScheduledAppointmentsWithDetails(
            long enterpriseServiceId) {
        return appointmentService.getEnterpriseServiceUncancelledFutureScheduledAppointments(enterpriseServiceId)
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
