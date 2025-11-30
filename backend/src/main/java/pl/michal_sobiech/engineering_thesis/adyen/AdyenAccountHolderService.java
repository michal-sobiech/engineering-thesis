package pl.michal_sobiech.engineering_thesis.adyen;

import java.time.LocalDate;

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

    public AccountHolder createAccountHolder(
            String firstName,
            String lastName,
            LocalDate dateOfBirth,
            String country,
            String city,
            String street,
            String postalCode) {
        LegalEntity legalEntity = adyenLegalEntityService.createIndividualLegalEntity(
                firstName, lastName, dateOfBirth, country, city, street, postalCode);

        AccountHolderInfo accountHolderInfo = new AccountHolderInfo()
                .legalEntityId(legalEntity.getId());

        try {
            return adyenAccountHoldersApi.createAccountHolder(accountHolderInfo);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

}
