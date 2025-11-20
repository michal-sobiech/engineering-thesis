package pl.michal_sobiech.engineering_thesis.appointment.custom.pending;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.appointment.AppointmentRepository;

@Service
@RequiredArgsConstructor
public class UncancelledPendingCustomAppointmentsService {

    private final AppointmentRepository appointmentRepository;

    public List<UncancelledPendingCustomAppointment> getCustomerUncancelledFuturePendingCustomAppointments(
            long customerUserId) {
        return appointmentRepository.findCustomerUncancelledFuturePendingCustomAppointments(customerUserId)
                .stream()
                .map(UncancelledPendingCustomAppointment::fromEntity)
                .collect(Collectors.toList());
    }

    public List<GetEnterpriseServiceUncancelledFuturePendingAppointmentsResponseRow> getEnterpriseServiceUncancelledFuturePendingAppointments(
            long enterpriseServiceId) {
        return appointmentRepository.findEnterpriseServiceUncancelledFuturePendingAppointments(enterpriseServiceId);
    }

}
