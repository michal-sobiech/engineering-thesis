package pl.michal_sobiech.engineering_thesis.adyen;

import java.time.LocalDate;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import com.adyen.model.balanceplatform.AccountHolder;
import com.adyen.model.balanceplatform.AccountHolderInfo;
import com.adyen.model.legalentitymanagement.LegalEntity;
import com.adyen.service.balanceplatform.AccountHoldersApi;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdyenAccountHolderService {

    private final AdyenLegalEntityService adyenLegalEntityService;
    private final AccountHoldersApi adyenAccountHoldersApi;

    public Pair<LegalEntity, AccountHolder> createLegalEntityAndAccountHolder(
            String firstName,
            String lastName,
            LocalDate dateOfBirth,
            String country,
            String city,
            String street,
            String postalCode) {
        LegalEntity legalEntity = adyenLegalEntityService.createLegalEntityForIndividual(
                firstName, lastName, dateOfBirth, country, city, street, postalCode);

        AccountHolderInfo accountHolderInfo = new AccountHolderInfo()
                .legalEntityId(legalEntity.getId());

        try {
            AccountHolder accountHolder = adyenAccountHoldersApi.createAccountHolder(accountHolderInfo);
            return Pair.of(legalEntity, accountHolder);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

}
