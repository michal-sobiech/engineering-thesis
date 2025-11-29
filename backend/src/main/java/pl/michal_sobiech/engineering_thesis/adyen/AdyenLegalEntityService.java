package pl.michal_sobiech.engineering_thesis.adyen;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.adyen.model.legalentitymanagement.Address;
import com.adyen.model.legalentitymanagement.BirthData;
import com.adyen.model.legalentitymanagement.Individual;
import com.adyen.model.legalentitymanagement.LegalEntity;
import com.adyen.model.legalentitymanagement.LegalEntityInfoRequiredType;
import com.adyen.model.legalentitymanagement.Name;
import com.adyen.service.legalentitymanagement.LegalEntitiesApi;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdyenLegalEntityService {

    private final LegalEntitiesApi adyenLegalEntitiesApi;

    public LegalEntity createIndividual(
            String firstName,
            String lastName,
            LocalDate dateOfBirth,
            String country,
            String city,
            String street,
            String postalCode) {
        Address address = new Address()
                .country(country)
                .city(city)
                .street(street)
                .postalCode(postalCode);

        Name name = new Name()
                .firstName(firstName)
                .lastName(lastName);

        BirthData birthData = new BirthData()
                .dateOfBirth(dateOfBirth.toString());

        Individual individual = new Individual()
                .residentialAddress(address)
                .name(name)
                .birthData(birthData);

        LegalEntityInfoRequiredType legalEntityInfoRequiredType = new LegalEntityInfoRequiredType()
                .individual(individual)
                .type(LegalEntityInfoRequiredType.TypeEnum.INDIVIDUAL);

        try {
            return adyenLegalEntitiesApi.createLegalEntity(legalEntityInfoRequiredType);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

}
