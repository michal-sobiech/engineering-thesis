package pl.michal_sobiech.payout_worker.password_encoder;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.password_encoder.PasswordEncoder;

@Component
@RequiredArgsConstructor
public class PasswordEncoderImpl implements PasswordEncoder {

    private final org.springframework.security.crypto.password.PasswordEncoder springPasswordEncoder;

    @Override
    public String encode(String passwordRaw) {
        return springPasswordEncoder.encode(passwordRaw);
    }

}
