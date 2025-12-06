package pl.michal_sobiech.payout_worker.payout;

import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;

@Service
public class PayoutService {

    public void payOutFunds(String destinationIban, Money money) {
        // TODO implementation - PSD2 regulations prevent easy creation of test
        // accounts, so this function remains empty
    }

}
