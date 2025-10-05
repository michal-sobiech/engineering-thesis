package pl.michal_sobiech.engineering_thesis.entrepreneur;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.SwaggerCodeGenExample.api.EntrepreneursApi;
import org.SwaggerCodeGenExample.model.CreateIndependentEndUserRequest;
import org.SwaggerCodeGenExample.model.EntrepreneurGetMeResponse;
import org.SwaggerCodeGenExample.model.GetEntrepreneurEnterprisesResponseItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.enterprise.Enterprise;
import pl.michal_sobiech.engineering_thesis.enterprise.EnterpriseService;
import pl.michal_sobiech.engineering_thesis.independent_end_user.IndependentEndUser;
import pl.michal_sobiech.engineering_thesis.independent_end_user.IndependentEndUserService;
import pl.michal_sobiech.engineering_thesis.user.UserIdAuthentication;
import pl.michal_sobiech.engineering_thesis.utils.AuthUtils;
import pl.michal_sobiech.engineering_thesis.utils.HttpUtils;

@RestController
@RequiredArgsConstructor
public class EntrepreneurController implements EntrepreneursApi {

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
        Optional<UserIdAuthentication> optionalAuthentication = AuthUtils.getUserIdAuthentication();
        if (optionalAuthentication.isEmpty()) {
            return HttpUtils.createUnauthorizedResponse();
        }
        UserIdAuthentication authentication = optionalAuthentication.get();
        long userId = authentication.getPrincipal();

        Optional<IndependentEndUser> optionalIndependentEndUser = independentEndUserService.findByUserId(userId);
        if (optionalIndependentEndUser.isEmpty()) {
            return HttpUtils.createForbiddenResponse();
        }
        IndependentEndUser independentEndUser = optionalIndependentEndUser.get();

        long independentEndUserId = independentEndUser.getIndependentEndUserId();
        Optional<Entrepreneur> optionalEntrepreneur = entrepreneurService
                .findByIndependentEndUserId(independentEndUserId);
        if (optionalEntrepreneur.isEmpty()) {
            return HttpUtils.createForbiddenResponse();
        }
        Entrepreneur entrepreneur = optionalEntrepreneur.get();

        var responseBody = new EntrepreneurGetMeResponse(
                entrepreneur.getIndependentEndUserId(),
                entrepreneur.getEntrepreneurId(),
                independentEndUser.getEmail(),
                independentEndUser.getFirstName(),
                independentEndUser.getLastName());
        return ResponseEntity.ok(responseBody);
    }

}
