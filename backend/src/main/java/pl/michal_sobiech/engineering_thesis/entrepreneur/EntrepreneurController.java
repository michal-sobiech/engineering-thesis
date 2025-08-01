package pl.michal_sobiech.engineering_thesis.entrepreneur;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.dto.create_entrepreneur.CreateEntrepreneurRequest;
import pl.michal_sobiech.engineering_thesis.dto.create_entrepreneur.CreateEntrepreneurResponse;

@RestController
@RequiredArgsConstructor
public class EntrepreneurController {

    private final EntrepreneurService service;

    @PostMapping("/create_entrepreneur")
    public CreateEntrepreneurResponse createEntrepreneur(
            @RequestBody CreateEntrepreneurRequest request) {
        Entrepreneur entrepreneur = service.createEntrepreneur(request);
        return CreateEntrepreneurResponse.from(entrepreneur);
    }

}
