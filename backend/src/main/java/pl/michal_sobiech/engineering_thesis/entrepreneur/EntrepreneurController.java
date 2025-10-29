package pl.michal_sobiech.engineering_thesis.entrepreneur;

import java.util.List;
import java.util.function.Function;

import org.SwaggerCodeGenExample.api.EntrepreneursApi;
import org.SwaggerCodeGenExample.model.CreateIndependentEndUserRequest;
import org.SwaggerCodeGenExample.model.GetEntrepreneurEnterprisesResponseItem;
import org.SwaggerCodeGenExample.model.IndependentEndUserGetMeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.auth.AuthService;
import pl.michal_sobiech.engineering_thesis.enterprise.EnterpriseEntity;
import pl.michal_sobiech.engineering_thesis.enterprise.EnterpriseService;

@RestController
@RequiredArgsConstructor
public class EntrepreneurController implements EntrepreneursApi {

    private final AuthService authService;
    private final EntrepreneurService entrepreneurService;
    private final EnterpriseService enterpriseService;

    @Override
    public ResponseEntity<List<GetEntrepreneurEnterprisesResponseItem>> getEntrepreneurEnterprises(
            Long entrepreneurUserId) {
        List<EnterpriseEntity> enterprises = enterpriseService.findAllByOwnerUserId(entrepreneurUserId);
        Function<EnterpriseEntity, GetEntrepreneurEnterprisesResponseItem> mapperFn = (
                EnterpriseEntity enterprise) -> new GetEntrepreneurEnterprisesResponseItem(
                        enterprise.getEnterpriseId(),
                        enterprise.getName());
        List<GetEntrepreneurEnterprisesResponseItem> mappedEnterprises = enterprises.stream().map(mapperFn).toList();
        return ResponseEntity.ok(mappedEnterprises);
    }

    @Override
    public ResponseEntity<Void> createEntrepreneur(
            CreateIndependentEndUserRequest request) {
        entrepreneurService.save(
                request.getEmail(),
                request.getFirstName(),
                request.getLastName(),
                request.getPassword());
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<IndependentEndUserGetMeResponse> getMeEntrepreneur() {
        Entrepreneur entrepreneur = authService.requireEntrepreneur();

        var responseBody = new IndependentEndUserGetMeResponse(
                entrepreneur.getUserId(),
                entrepreneur.getEmail(),
                entrepreneur.getFirstName(),
                entrepreneur.getLastName());
        return ResponseEntity.ok(responseBody);
    }

}
