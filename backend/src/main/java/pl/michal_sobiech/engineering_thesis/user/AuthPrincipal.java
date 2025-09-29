package pl.michal_sobiech.engineering_thesis.user;

import java.util.List;

public record AuthPrincipal(long userId, List<Role> roles) {
}
