package pl.michal_sobiech.engineering_thesis.user;

import org.SwaggerCodeGenExample.api.UsersApi;
import org.SwaggerCodeGenExample.model.GetMyUserGroup200Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.user.User;
import pl.michal_sobiech.core.user.UserGroup;
import pl.michal_sobiech.engineering_thesis.auth.AuthService;

@RestController
@RequiredArgsConstructor
public class UserController implements UsersApi {

    private final AuthService authService;

    @Override
    public ResponseEntity<GetMyUserGroup200Response> getMyUserGroup() {
        User user = authService.requireAuthorizedUser();
        UserGroup group = user.getUserGroup();
        var body = new GetMyUserGroup200Response(group.name());
        return ResponseEntity.ok(body);
    }

}
