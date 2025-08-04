package pl.michal_sobiech.engineering_thesis.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.jwt.JwtAuthRequestFilter;
import pl.michal_sobiech.engineering_thesis.security.cors.CorsSetupper;
import pl.michal_sobiech.engineering_thesis.security.csrf.CsrfSetupper;
import pl.michal_sobiech.engineering_thesis.security.no_auth_endpoints.NoAuthEndpointsSetupper;
import pl.michal_sobiech.engineering_thesis.security.session_management.SessionManagementSetupper;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthRequestFilter jwtAuthRequestFilter;
    private final UserDetailsService userDetailsService;
    private final CorsSetupper corsSetupper;
    private final CsrfSetupper csrfSetupper;
    private final SessionManagementSetupper sessionManagementSetupper;
    private final NoAuthEndpointsSetupper noAuthEndpointsSetupper;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        httpSecurity = corsSetupper.setUpCors(httpSecurity);
        httpSecurity = csrfSetupper.setupCsrf(httpSecurity);
        httpSecurity = sessionManagementSetupper.setupSessionManagement(httpSecurity);
        httpSecurity.addFilterBefore(jwtAuthRequestFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity = noAuthEndpointsSetupper.setupNoAuthEndpoints(httpSecurity);
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
