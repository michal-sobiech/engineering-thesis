package pl.michal_sobiech.engineering_thesis.entrepreneur;

import java.util.List;
import java.util.function.Function;

import org.SwaggerCodeGenExample.api.EntrepreneursApi;
import org.SwaggerCodeGenExample.model.CreateIndependentEndUserRequest;
import org.SwaggerCodeGenExample.model.EntrepreneurGetMeResponse;
import org.SwaggerCodeGenExample.model.GetEntrepreneurEnterprisesResponseItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.auth.AuthService;
import pl.michal_sobiech.engineering_thesis.auth.EntrepreneurAuthContext;
import pl.michal_sobiech.engineering_thesis.enterprise.Enterprise;
import pl.michal_sobiech.engineering_thesis.enterprise.EnterpriseService;
import pl.michal_sobiech.engineering_thesis.independent_end_user.IndependentEndUser;
import pl.michal_sobiech.engineering_thesis.independent_end_user.IndependentEndUserService;

@RestController
@RequiredArgsConstructor
public class EntrepreneurController implements EntrepreneursApi {

    private final AuthService authService;
    private final EntrepreneurService entrepreneurService;
    private final IndependentEndUserService independentEndUserService;
    private final EnterpriseService enterpriseService;

    @Override
    public ResponseEntity<List<GetEntrepreneurEnterprisesResponseItem>> getEntrepreneurEnterprises(
            Integer entrepreneurId) {
        List<Enterprise> enterprises = enterpriseService.findAllByEntrepreneurId(entrepreneurId);
        Function<Enterprise, GetEntrepreneurEnterprisesResponseItem> mapperFn = (
                Enterprise enterprise) -> new GetEntrepreneurEnterprisesResponseItem(
                        enterprise.getEnterpriseId(),
                        enterprise.getName());
        List<GetEntrepreneurEnterprisesResponseItem> mappedEnterprises = enterprises.stream().map(mapperFn).toList();
        return ResponseEntity.ok(mappedEnterprises);
    }

    @Override
    public ResponseEntity<Void> createEntrepreneur(
            CreateIndependentEndUserRequest createIndependentEndUserRequest) {
        entrepreneurService.createEntrepreneur(createIndependentEndUserRequest);
        return ResponseEntity.ok().build();
    }

    // TODO this returns code 500 unexpectedly
    @Override
    public ResponseEntity<EntrepreneurGetMeResponse> getMeEntrepreneur() {
        EntrepreneurAuthContext entrepreneurAuthContext = authService.requireEntrepreneur();
        IndependentEndUser independentEndUser = entrepreneurAuthContext.independentEndUser();
        Entrepreneur entrepreneur = entrepreneurAuthContext.entrepreneur();

        var responseBody = new EntrepreneurGetMeResponse(
                entrepreneur.getIndependentEndUserId(),
                entrepreneur.getEntrepreneurId(),
                independentEndUser.getEmail(),
                independentEndUser.getFirstName(),
                independentEndUser.getLastName());
        return ResponseEntity.ok(responseBody);
    }

}
