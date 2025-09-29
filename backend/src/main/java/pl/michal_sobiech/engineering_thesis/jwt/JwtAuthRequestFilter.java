package pl.michal_sobiech.engineering_thesis.jwt;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.user.UserIdAuthentication;
import pl.michal_sobiech.engineering_thesis.utils.AuthUtils;

@RequiredArgsConstructor
public class JwtAuthRequestFilter extends OncePerRequestFilter {

    private final JwtDecoder jwtDecoder;

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
                .map(jwtDecoder::decode)
                .map(claims -> claims.getSubject())
                .map(this::parseSubjectToInt)
                .map(this::createAuthentication)
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

    private int parseSubjectToInt(String userId) {
        try {
            return Integer.parseInt(userId);
        } catch (Exception exception) {
            throw new IllegalStateException("Couldn't parse subject to int");
        }
    }

    private Authentication createAuthentication(int userId) {
        return new UserIdAuthentication(userId);
    }

    private void setAuthentication(Authentication auth) {
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

}
