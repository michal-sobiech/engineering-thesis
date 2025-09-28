package pl.michal_sobiech.engineering_thesis.independent_end_user;

import org.SwaggerCodeGenExample.api.IndependentEndUsersApi;
import org.SwaggerCodeGenExample.model.CheckIndependentEndUserEmailExists200Response;
import org.SwaggerCodeGenExample.model.CreateIndependentEndUserRequest;
import org.SwaggerCodeGenExample.model.CreateIndependentEndUserResponse;
import org.SwaggerCodeGenExample.model.CreateIndependentEndUserResponseUser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.jwt.JwtCreationService;

@Controller
@RequiredArgsConstructor
public class IndependentEndUserController implements IndependentEndUsersApi {

    private final IndependentEndUserService service;
    private final JwtCreationService jwtService;

    @Override
    public ResponseEntity<CreateIndependentEndUserResponse> createIndependentEndUser(
            CreateIndependentEndUserRequest createIndependentEndUserRequest) {
        IndependentEndUser createdUser = service.createIndependentEndUser(createIndependentEndUserRequest);
        CreateIndependentEndUserResponseUser userDto = new CreateIndependentEndUserResponseUser(
                createdUser.getFirstName(), createdUser.getLastName(), createdUser.getEmail());

        String userIdAsString = Long.toString(createdUser.getId());
        String jwtToken = jwtService.generateTokenNow(userIdAsString);

        CreateIndependentEndUserResponse responseBody = new CreateIndependentEndUserResponse(jwtToken, userDto);
        return ResponseEntity.ok(responseBody);
    }

    @Override
    public ResponseEntity<CheckIndependentEndUserEmailExists200Response> checkIndependentEndUserEmailExists(
            String email) {
        final boolean exists = service.checkIndependentEndUserEmailExists(email);
        final var responseBody = new CheckIndependentEndUserEmailExists200Response(exists);
        return ResponseEntity.ok(responseBody);
    }

}
