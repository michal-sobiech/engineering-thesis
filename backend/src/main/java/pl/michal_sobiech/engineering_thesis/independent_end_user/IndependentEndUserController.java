package pl.michal_sobiech.engineering_thesis.independent_end_user;

import java.util.Optional;

import org.SwaggerCodeGenExample.api.IndependentEndUsersApi;
import org.SwaggerCodeGenExample.model.CheckIndependentEndUserEmailExists200Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class IndependentEndUserController implements IndependentEndUsersApi {

    private final IndependentEndUserService independentEndUserService;

    @Override
    public ResponseEntity<CheckIndependentEndUserEmailExists200Response> checkIndependentEndUserEmailExists(
            String email) {
        Optional<IndependentEndUser> independentEndUser = independentEndUserService.findByEmail(email);
        var body = new CheckIndependentEndUserEmailExists200Response(independentEndUser.isPresent());
        return ResponseEntity.ok(body);
    }

}
