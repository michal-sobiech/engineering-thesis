package pl.michal_sobiech.engineering_thesis.jwt;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.username_authentication_token.User;
import pl.michal_sobiech.engineering_thesis.username_authentication_token.UsernameAuthenticationToken;
import pl.michal_sobiech.engineering_thesis.utils.AuthUtils;

@RequiredArgsConstructor
public class JwtAuthRequestFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

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
                .flatMap(this::getJwtToken)
                .map(jwtService::parseToken)
                .ifPresent(parsedToken -> setAuthentication(parsedToken.subject(), parsedToken.scope()));
    }

    private void checkUserAlreadyAuthenticated() {
        if (AuthUtils.isUserAlreadyAuthenticated()) {
            String exceptionMessage = "User is already authenticated";
            throw new IllegalStateException(exceptionMessage);
        }
    }

    private Optional<String> getJwtToken(String header) {
        return header.startsWith("Bearer ")
                ? Optional.of(header.substring(7))
                : Optional.empty();
    }

    private void setAuthentication(String subject, String scope) {
        Object principal = new User(subject, scope, null);
        Authentication authenticationToken = new UsernameAuthenticationToken(principal);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

}
