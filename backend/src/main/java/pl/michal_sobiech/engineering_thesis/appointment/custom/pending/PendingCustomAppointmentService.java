package pl.michal_sobiech.engineering_thesis.appointment.custom.pending;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.appointment.AppointmentEntity;
import pl.michal_sobiech.engineering_thesis.appointment.custom.CustomAppointmentQueryService;
import pl.michal_sobiech.engineering_thesis.appointment.query.AppointmentQueryTimeRange;
import pl.michal_sobiech.engineering_thesis.appointment.query.CustomAppointmentStatus;

@Service
@RequiredArgsConstructor
public class PendingCustomAppointmentService {

    private final CustomAppointmentQueryService customAppointmentQueryService;

    public List<UncancelledPendingCustomAppointment> getCustomerUncancelledFuturePendingCustomAppointments(
            long customerUserId) {
        Function<AppointmentEntity, UncancelledPendingCustomAppointment> factoryFunction = record -> {
            return UncancelledPendingCustomAppointment.fromEntity(record).orElseThrow();
        };

        return customAppointmentQueryService.getCustomAppointments(
                Optional.of(customerUserId),
                Optional.empty(),
                Optional.empty(),
                Optional.of(false),
                Optional.of(AppointmentQueryTimeRange.FUTURE),
                CustomAppointmentStatus.PENDING,
                factoryFunction);
    }
}
