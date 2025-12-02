package pl.michal_sobiech.payout_worker.payout;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PayoutWorker {

    private static final int DELAY_MIN = 10;

    private final PayoutService payoutService;

    @Scheduled(fixedDelay = DELAY_MIN * 60 * 1000)
    public void findAndProcessFinishedAppointments() {

    }

}
