package pl.michal_sobiech.engineering_thesis.adyen;

import java.util.Currency;

import org.springframework.stereotype.Service;

import com.adyen.model.balanceplatform.Amount;
import com.adyen.model.transfers.CounterpartyInfoV3;
import com.adyen.model.transfers.TransferInfo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdyenPayoutService {

    private final AdyenAccountHolderService adyenAccountHolderService;

    public void transferToBalanceAccount(
            String balanceAccountId,
            long amountMinorUnits,
            Currency currency) {

        Amount amount = new Amount()
                .value(amountMinorUnits)
                .currency(currency.toString());

        CounterpartyInfoV3 counterpartyInfo = new CounterpartyInfoV3()
                .balanceAccountId(balanceAccountId);

        TransferInfo transferInfo = new TransferInfo()
                .balanceAccountId(balanceAccountId)
                .counterparty(counterpartyInfo);
    }

}
