package pl.michal_sobiech.engineering_thesis.secret;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import pl.michal_sobiech.core.secret.Secret;

@Component
@ConfigurationPropertiesBinding
public class SecretConverter implements Converter<String, Secret<String>> {

    @Override
    public Secret<String> convert(String value) {
        return new Secret<String>(value);
    }

}
