import java.util.List;
import java.util.stream.Collectors;

import org.SwaggerCodeGenExample.api.RegularAdminsApi;
import org.SwaggerCodeGenExample.model.CreateIndependentEndUserRequest;
import org.SwaggerCodeGenExample.model.GetRegularAdminResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RegularAdminController implements RegularAdminsApi {

    private final RegularAdminsService regularAdminsService;

    @Override
    public ResponseEntity<Void> createRegularAdmin(CreateIndependentEndUserRequest request) {
        // TODO
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<GetRegularAdminResponse>> getRegularAdmins() {
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