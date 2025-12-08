package pl.michal_sobiech.core.payment;

import java.util.List;
import java.util.stream.Collectors;

import org.javamoney.moneta.Money;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.appointment.AppointmentEntity;
import pl.michal_sobiech.core.appointment.AppointmentRepository;
import pl.michal_sobiech.core.appointment.UncancelledScheduledAppointment;
import pl.michal_sobiech.core.entrepreneur.Entrepreneur;
import pl.michal_sobiech.core.entrepreneur.EntrepreneurService;
import pl.michal_sobiech.core.payout.UserPayoutService;

@RequiredArgsConstructor
public class AppointmentPayoutProcessingService {

    private final AppointmentRepository appointmentRepository;
    private final EntrepreneurService entrepreneurService;
    private final UserPayoutService userPayoutService;

    public void processPastScheduledAppointmentsWaitingForPayoutProcessing() {
        getPastScheduledAppointmentsWaitingForPayoutProcessing()
                .forEach(appointment -> {
                    long appointmentId = appointment.appointmentId();
                    Entrepreneur entrepreneur = entrepreneurService.getEnterpriseOwner(appointmentId);

                    Money price = Money.of(appointment.price(), appointment.currency().toString());
                    userPayoutService.payUser(entrepreneur.getUserId(), price);
                    setAppointmentPayoutProcessed(appointmentId);
                });
    }

    private List<UncancelledScheduledAppointment> getPastScheduledAppointmentsWaitingForPayoutProcessing() {
        return appointmentRepository.findPastScheduledAppointmentsWaitingForPayoutProcessing()
                .stream()
                .map(UncancelledScheduledAppointment::fromEntityOrThrow)
                .collect(Collectors.toList());
    }

    private void setAppointmentPayoutProcessed(long appointmentId) {
        AppointmentEntity record = appointmentRepository.findById(appointmentId).orElseThrow();
        record.setWasPayoutProcessed(true);
        appointmentRepository.save(record);
    }

}
