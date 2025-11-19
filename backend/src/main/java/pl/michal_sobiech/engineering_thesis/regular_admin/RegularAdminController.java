package pl.michal_sobiech.engineering_thesis.regular_admin;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.SwaggerCodeGenExample.api.RegularAdminsApi;
import org.SwaggerCodeGenExample.model.CreateIndependentEndUserRequest;
import org.SwaggerCodeGenExample.model.GetRegularAdminResponse;
import org.SwaggerCodeGenExample.model.PatchRegularAdminRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.auth.AuthService;
import pl.michal_sobiech.engineering_thesis.user.UserService;

@RestController
@RequiredArgsConstructor
public class RegularAdminController implements RegularAdminsApi {

    private final RegularAdminsService regularAdminsService;
    private final UserService userService;
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

    @Override
    public ResponseEntity<GetRegularAdminResponse> getRegularAdmin(Long adminUserId) {
        authService.requireHeadAdmin();

        RegularAdmin regularAdmin = regularAdminsService.getRegularAdmin(adminUserId).orElseThrow();

        GetRegularAdminResponse body = new GetRegularAdminResponse(
                regularAdmin.getUserId(),
                regularAdmin.getUsername(),
                regularAdmin.getFirstName(),
                regularAdmin.getLastName());
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<Void> patchRegularAdmin(Long adminUserId, PatchRegularAdminRequest request) {
        userService.patch(
                adminUserId,
                Optional.ofNullable(request.getUsername()),
                Optional.ofNullable(request.getLastName()),
                Optional.ofNullable(request.getLastName()),
                Optional.ofNullable(request.getPassword()));
        return ResponseEntity.ok().build();
    }

}