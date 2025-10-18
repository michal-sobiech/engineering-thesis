package pl.michal_sobiech.engineering_thesis.converter;

import java.util.Currency;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CurrencyConverter implements AttributeConverter<Currency, String> {

    @Override
    public String convertToDatabaseColumn(Currency attribute) {
        return attribute == null
                ? null
                : attribute.getCurrencyCode();
    }

    @Override
    public Currency convertToEntityAttribute(String databaseValue) {
        return databaseValue == null
                ? null
                : Currency.getInstance(databaseValue);
    }

}
