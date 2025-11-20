package pl.michal_sobiech.engineering_thesis.appointment.custom.rejected;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.appointment.custom.CustomAppointmentQueryService;

@Service
@RequiredArgsConstructor
public class RejectedCustomAppointmentsService {

    private final CustomAppointmentQueryService customAppointmentQueryService;

    public List<RejectedCustomAppointment> getCustomerFutureRejectedCustomAppointmentsOf(long customerUserId) {
        return customAppointmentQueryService.
                .stream()
                .map(RejectedCustomAppointment::fromEntity)
                .collect(Collectors.toList());
    }

}
