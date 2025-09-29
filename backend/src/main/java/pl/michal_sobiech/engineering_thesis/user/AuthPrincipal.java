package pl.michal_sobiech.engineering_thesis.user;

public record AuthPrincipal(
    long userId,
    Role role,
) {
}
