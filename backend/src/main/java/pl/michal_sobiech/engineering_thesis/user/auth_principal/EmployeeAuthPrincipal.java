package pl.michal_sobiech.engineering_thesis.user.auth_principal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class EmployeeAuthPrincipal implements AuthPrincipal {

    private long userId;
    private long employeeId;

}
