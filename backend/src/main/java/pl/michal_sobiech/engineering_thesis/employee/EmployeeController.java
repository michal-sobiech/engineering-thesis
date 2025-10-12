package pl.michal_sobiech.engineering_thesis.employee;

import java.util.Optional;

import org.SwaggerCodeGenExample.api.EnterpriseEmployeeApi;
import org.SwaggerCodeGenExample.model.EmployeeGetMeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.auth.AuthService;
import pl.michal_sobiech.engineering_thesis.utils.HttpUtils;

@RestController
@RequiredArgsConstructor
public class EmployeeController implements EnterpriseEmployeeApi {

    private final AuthService authService;
    private final EmployeeService employeeService;

    @Override
    public ResponseEntity<EmployeeGetMeResponse> getMeEmployee() {
        long userId = authService.requireAuthorizedUser();

        Optional<Employee> optionalEmployee = employeeService.findByUserId(userId);
        if (optionalEmployee.isEmpty()) {
            return HttpUtils.createNotFoundReponse();
        }
        Employee employee = optionalEmployee.get();

        final var responseBody = new EmployeeGetMeResponse(
                employee.getUserId(),
                employee.getEmployeeId(),
                employee.getEnterpriseId(),
                employee.getUsername(),
                employee.getFirstName(),
                employee.getLastName());

        return ResponseEntity.ok(responseBody);
    }

}
