package pl.michal_sobiech.engineering_thesis.employee;

import org.SwaggerCodeGenExample.api.EnterpriseEmployeeApi;
import org.SwaggerCodeGenExample.model.EmployeeGetMeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.auth.AuthService;
import pl.michal_sobiech.engineering_thesis.user.UserDomain;
import pl.michal_sobiech.engineering_thesis.user.UserService;

@RestController
@RequiredArgsConstructor
public class EmployeeController implements EnterpriseEmployeeApi {

    private final AuthService authService;
    private final UserService userService;

    @Override
    public ResponseEntity<EmployeeGetMeResponse> getMeEmployee() {
        UserDomain user = authService.requireAuthorizedUser();

        final var responseBody = new EmployeeGetMeResponse(
                user.getUserId(),
                user.getEnterpriseId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName());

        return ResponseEntity.ok(responseBody);
    }

}
