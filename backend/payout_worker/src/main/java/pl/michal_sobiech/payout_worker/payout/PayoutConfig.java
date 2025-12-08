package pl.michal_sobiech.payout_worker.payout;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pl.michal_sobiech.core.appointment.AppointmentRepository;
import pl.michal_sobiech.core.entrepreneur.EntrepreneurService;
import pl.michal_sobiech.core.payment.AppointmentPayoutProcessingService;
import pl.michal_sobiech.core.payout.PayoutService;
import pl.michal_sobiech.core.payout.UserPayoutService;

@Configuration
public class PayoutConfig {

    @Bean
    public PayoutService payoutService() {
        return new PayoutService();
    }

    @Bean
    public UserPayoutService UserPayoutService(
            EntrepreneurService entrepreneurService,
            PayoutService payoutService) {
        return new UserPayoutService(entrepreneurService, payoutService);
    }

    @Bean
    public AppointmentPayoutProcessingService appointmentPayoutProcessingService(
            AppointmentRepository appointmentRepository,
            EntrepreneurService entrepreneurService,
            UserPayoutService userPayoutService) {
        return new AppointmentPayoutProcessingService(appointmentRepository, entrepreneurService, userPayoutService);
    }

}
