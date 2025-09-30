package pl.michal_sobiech.engineering_thesis.independent_end_user;

import org.SwaggerCodeGenExample.api.IndependentEndUsersApi;
import org.SwaggerCodeGenExample.model.CheckIndependentEndUserEmailExists200Response;
import org.SwaggerCodeGenExample.model.CreateIndependentEndUserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.utils.HttpUtils;

@Controller
@RequiredArgsConstructor
public class IndependentEndUserController implements IndependentEndUsersApi {

    private final IndependentEndUserService independentEndUserService;

    // TODO should this endpoint even exist?
    @Override
    public ResponseEntity<Void> createIndependentEndUser(
            CreateIndependentEndUserRequest createIndependentEndUserRequest) {
        boolean isEmailTaken = independentEndUserService
                .checkIndependentEndUserEmailExists(createIndependentEndUserRequest.getEmail());
        if (isEmailTaken) {
            return HttpUtils.createConflictResponse();
        }

        independentEndUserService.createIndependentEndUser(createIndependentEndUserRequest);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<CheckIndependentEndUserEmailExists200Response> checkIndependentEndUserEmailExists(
            String email) {
        final boolean exists = independentEndUserService.checkIndependentEndUserEmailExists(email);
        final var responseBody = new CheckIndependentEndUserEmailExists200Response(exists);
        return ResponseEntity.ok(responseBody);
    }

}
