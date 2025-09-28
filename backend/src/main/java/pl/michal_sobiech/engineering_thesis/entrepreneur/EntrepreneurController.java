package pl.michal_sobiech.engineering_thesis.entrepreneur;

import java.util.ArrayList;
import java.util.List;

import org.SwaggerCodeGenExample.api.EntrepreneursApi;
import org.SwaggerCodeGenExample.model.GetEntrepreneurEnterprisesResponseItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class EntrepreneurController implements EntrepreneursApi {

    private final EntrepreneurService service;

    @Override
    public ResponseEntity<List<GetEntrepreneurEnterprisesResponseItem>> getEntrepreneurEnterprises(
            Integer entrepreneurId) {
        final List<GetEntrepreneurEnterprisesResponseItem> responseBody = new ArrayList<>();
        return ResponseEntity.ok(responseBody);
    }

}
