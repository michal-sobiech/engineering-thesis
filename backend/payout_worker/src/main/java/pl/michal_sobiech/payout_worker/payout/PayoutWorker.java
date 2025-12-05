package pl.michal_sobiech.payout_worker.payout;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.appointment.AppointmentService;
import pl.michal_sobiech.core.entrepreneur.Entrepreneur;
import pl.michal_sobiech.core.entrepreneur.EntrepreneurService;
import pl.michal_sobiech.payout_worker.user.UserPayoutService;

@Component
@RequiredArgsConstructor
public class PayoutWorker {

    private static final int DELAY_MIN = 10;

    private final EntrepreneurService entrepreneurService;
    private final UserPayoutService userPayoutService;
    private final AppointmentService appointmentService;

    @Scheduled(fixedDelay = DELAY_MIN * 60 * 1000)
    public void findAndProcessFinishedAppointments() {
        appointmentService.getPastScheduledAppointmentsWaitingForPayoutProcessing()
                .forEach(appointment -> {
                    long appointmentId = appointment.appointmentId();
                    Entrepreneur entrepreneur = entrepreneurService.getEnterpriseOwner(appointmentId);
                    userPayoutService.payUser(entrepreneur.getUserId());
                    appointmentService.setAppointmentPayoutProcessed(appointmentId);
                });
    }

}
