package pl.michal_sobiech.engineering_thesis.independent_end_user;

import org.SwaggerCodeGenExample.api.IndependentEndUsersApi;
import org.SwaggerCodeGenExample.model.CheckIndependentEndUserEmailExists200Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class IndependentEndUserController implements IndependentEndUsersApi {

    private final IndependentEndUserService independentEndUserService;

    @Override
    public ResponseEntity<CheckIndependentEndUserEmailExists200Response> checkIndependentEndUserEmailExists(
            String email) {
        final boolean exists = independentEndUserService.checkIndependentEndUserEmailExists(email);
        final var responseBody = new CheckIndependentEndUserEmailExists200Response(exists);
        return ResponseEntity.ok(responseBody);
    }

}
