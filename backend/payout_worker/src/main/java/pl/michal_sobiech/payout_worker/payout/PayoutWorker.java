package pl.michal_sobiech.payout_worker.payout;

import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.payment.AppointmentPayoutProcessingService;

@Component
@RequiredArgsConstructor
public class PayoutWorker {

    private static final int DELAY_S = 5;

    private final AppointmentPayoutProcessingService appointmentPaymentProcessingService;

    @Scheduled(fixedDelay = DELAY_S, timeUnit = TimeUnit.SECONDS)
    public void findAndProcessFinishedAppointments() {
        appointmentPaymentProcessingService.processPastScheduledAppointmentsWaitingForPayoutProcessing();
    }

}
