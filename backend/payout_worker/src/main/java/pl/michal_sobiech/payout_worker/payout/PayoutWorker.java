package pl.michal_sobiech.payout_worker.payout;

import java.util.concurrent.TimeUnit;

import org.javamoney.moneta.Money;
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

    private static final int DELAY_S = 5;

    private final EntrepreneurService entrepreneurService;
    private final UserPayoutService userPayoutService;
    private final AppointmentService appointmentService;

    @Scheduled(fixedDelay = DELAY_S, timeUnit = TimeUnit.SECONDS)
    public void findAndProcessFinishedAppointments() {
        System.out.println("SCHEDULED TASK!!");
        appointmentService.getPastScheduledAppointmentsWaitingForPayoutProcessing()
                .forEach(appointment -> {
                    long appointmentId = appointment.appointmentId();
                    Entrepreneur entrepreneur = entrepreneurService.getEnterpriseOwner(appointmentId);

                    Money price = Money.of(appointment.price(), appointment.currency().toString());
                    userPayoutService.payUser(entrepreneur.getUserId(), price);
                    appointmentService.setAppointmentPayoutProcessed(appointmentId);
                });
    }

}
