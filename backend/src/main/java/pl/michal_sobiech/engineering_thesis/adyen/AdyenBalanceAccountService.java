package pl.michal_sobiech.engineering_thesis.adyen;

import java.time.LocalDate;

import org.apache.commons.lang3.tuple.Triple;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import com.adyen.model.balanceplatform.AccountHolder;
import com.adyen.model.balanceplatform.BalanceAccount;
import com.adyen.model.balanceplatform.BalanceAccountInfo;
import com.adyen.model.legalentitymanagement.LegalEntity;
import com.adyen.service.balanceplatform.BalanceAccountsApi;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdyenBalanceAccountService {

    private final AdyenAccountHolderService adyenAccountHolderService;
    private final BalanceAccountsApi adyenBalanceAccountsApi;

    public Triple<LegalEntity, AccountHolder, BalanceAccount> createLegalEntityAccountHolderAndBalanceAccount(
            String firstName,
            String lastName,
            LocalDate dateOfBirth,
            String country,
            String city,
            String street,
            String postalCode) {
        Pair<LegalEntity, AccountHolder> legalEntityAndAccountHolder = adyenAccountHolderService
                .createLegalEntityAndAccountHolder(
                        firstName, lastName, dateOfBirth, country, city, street, postalCode);
        LegalEntity legalEntity = legalEntityAndAccountHolder.getFirst();
        AccountHolder accountHolder = legalEntityAndAccountHolder.getSecond();

        BalanceAccountInfo balanceAccountInfo = new BalanceAccountInfo()
                .accountHolderId(accountHolder.getId());

        try {
            BalanceAccount balanceAccount = adyenBalanceAccountsApi.createBalanceAccount(balanceAccountInfo);
            return Triple.of(legalEntity, accountHolder, balanceAccount);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

}
