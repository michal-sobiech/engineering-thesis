package pl.michal_sobiech.engineering_thesis.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.jwt.JwtAuthRequestFilter;
import pl.michal_sobiech.engineering_thesis.security.cors.CorsSetupper;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthRequestFilter jwtAuthRequestFilter;
    private final UserDetailsService userDetailsService;
    private final CorsSetupper corsSetupper;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        httpSecurity = corsSetupper.setUpCors(httpSecurity);
        return buildSecurityFilterChain(httpSecurity);
    }

    private SecurityFilterChain buildSecurityFilterChain(HttpSecurity httpSecurity) {
        try {
            return httpSecurity.build();
        } catch (Exception exception) {
            String message = "Couldn't build SecurityFilterChain";
            throw new IllegalStateException(message, exception);
        }
    }

}
