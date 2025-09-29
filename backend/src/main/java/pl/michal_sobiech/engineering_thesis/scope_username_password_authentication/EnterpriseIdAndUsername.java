package pl.michal_sobiech.engineering_thesis.scope_username_password_authentication;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class EnterpriseIdAndUsername {

    private final long enterpriseId;
    private final String username;

}
