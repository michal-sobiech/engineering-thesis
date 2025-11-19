import java.util.List;
import java.util.stream.Collectors;

import org.SwaggerCodeGenExample.api.RegularAdminsApi;
import org.SwaggerCodeGenExample.model.CreateIndependentEndUserRequest;
import org.SwaggerCodeGenExample.model.GetRegularAdminResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.auth.AuthService;

@RestController
@RequiredArgsConstructor
public class RegularAdminController implements RegularAdminsApi {

    private final RegularAdminsService regularAdminsService;
    private final AuthService authService;

    @Override
    public ResponseEntity<Void> createRegularAdmin(CreateIndependentEndUserRequest request) {
        // TODO maybe do the authorization in security chain?
        authService.requireHeadAdmin();

        regularAdminsService.createRegularAdmin(
                request.getEmail(),
                request.getFirstName(),
                request.getLastName(),
                request.getPassword());
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<GetRegularAdminResponse>> getRegularAdmins() {
        authService.requireHeadAdmin();

        var body = regularAdminsService.getRegularAdmins()
                .stream()
                .map(admin -> new GetRegularAdminResponse(
                        admin.getUserId(),
                        admin.getUsername(),
                        admin.getFirstName(),
                        admin.getLastName()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(body);
    }

}