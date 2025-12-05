package pl.michal_sobiech.core.appointment.custom;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.appointment.ScheduledAppointmentWithDetails;
import pl.michal_sobiech.core.customer.Customer;
import pl.michal_sobiech.core.customer.CustomerService;
import pl.michal_sobiech.core.payment.payment_status.PaymentStatus;
import pl.michal_sobiech.core.payment.payment_status.PaymentStatusNotPaid;

@RequiredArgsConstructor
public class CustomAppointmentWithDetailsService {

    private final CustomAppointmentQueryService customAppointmentQueryService;
    private final CustomerService customerService;

    public List<ScheduledAppointmentWithDetails> getEnterpriseServiceUncancelledPendingAppointmentsWithDetails(
            long enterpriseServiceId) {
        return customAppointmentQueryService
                .getEnterpriseServiceUncancelledFuturePendingAppointments(enterpriseServiceId)
                .stream()
                .map(appointment -> {
                    Customer customer = customerService.getByUserId(appointment.customerUserId()).orElseThrow();

                    PaymentStatus paymentStatus = new PaymentStatusNotPaid();

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
                            paymentStatus);
                })
                .collect(Collectors.toList());
    }

}
