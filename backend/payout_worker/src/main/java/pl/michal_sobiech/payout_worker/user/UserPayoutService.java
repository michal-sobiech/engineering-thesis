package pl.michal_sobiech.payout_worker.user;

import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.entrepreneur.Entrepreneur;
import pl.michal_sobiech.core.entrepreneur.EntrepreneurService;
import pl.michal_sobiech.payout_worker.payout.PayoutService;

@Service
@RequiredArgsConstructor
public class UserPayoutService {

    private final EntrepreneurService entrepreneurService;
    private final PayoutService payoutService;

    public void payUser(long userId, Money amount) {
        // Only entrepreneurs are eligible for payout

        Entrepreneur entrepreneur = entrepreneurService.getByUserId(userId)
                .orElseThrow(() -> {
                    String message = "Given user id does not point to a user eligible for payout: %s".formatted(userId);
                    throw new IllegalArgumentException(message);
                });

        payEntrepreneur(entrepreneur, amount);
    }

    private void payEntrepreneur(Entrepreneur entrepreneur, Money amount) {
        String entrepreneurIban = entrepreneur.getIban();

        payoutService.payOutFunds(entrepreneurIban, amount);
    }

}
