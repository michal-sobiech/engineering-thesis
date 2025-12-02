package pl.michal_sobiech.payout_worker.payout;

import java.util.Currency;

import org.springframework.stereotype.Service;

@Service
public class PayoutService {

    public void payOutFunds(String destinationIban, long priceMinorUnits, Currency currency) {
        // TODO implementation - PSD2 regulations prevent easy creation of test
        // accounts, so this function remains empty
    }

}
