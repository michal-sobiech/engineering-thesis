package pl.michal_sobiech.engineering_thesis.user.auth_principal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class EntrepreneurAuthPrincipal implements AuthPrincipal {

    private final long userId;
    private final long entrepreneurId;

}
