package pl.michal_sobiech.engineering_thesis.jwt;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.security.authentication.jwt.JwtAuthentication;
import pl.michal_sobiech.engineering_thesis.security.authentication.jwt.JwtAuthenticationProvider;
import pl.michal_sobiech.engineering_thesis.user.UserIdAuthentication;
import pl.michal_sobiech.engineering_thesis.utils.AuthUtils;

@Component
@RequiredArgsConstructor
public class JwtAuthRequestFilter extends OncePerRequestFilter {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        tryToSetAuthentication(request, response, filterChain);
        filterChain.doFilter(request, response);
    }

    private void tryToSetAuthentication(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) {
        checkUserAlreadyAuthenticated();
        Optional.ofNullable(request.getHeader("Authorization"))
                .flatMap(this::extractJwtFromHeader)
                .map(JwtAuthentication::new)
                .flatMap(authToken -> {
                    try {
                        UserIdAuthentication authentication = jwtAuthenticationProvider.authenticate(authToken);
                        return Optional.of(authentication);
                    } catch (Exception exception) {
                        return Optional.empty();
                    }
                })
                .ifPresent(this::setAuthentication);
    }

    private void checkUserAlreadyAuthenticated() {
        if (AuthUtils.isUserAlreadyAuthenticated()) {
            String exceptionMessage = "User is already authenticated";
            throw new IllegalStateException(exceptionMessage);
        }
    }

    private Optional<String> extractJwtFromHeader(String header) {
        return header.startsWith("Bearer ")
                ? Optional.of(header.substring(7))
                : Optional.empty();
    }

    private void setAuthentication(Authentication auth) {
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

}
