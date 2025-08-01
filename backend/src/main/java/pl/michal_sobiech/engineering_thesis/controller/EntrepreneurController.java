package pl.michal_sobiech.engineering_thesis.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import pl.michal_sobiech.engineering_thesis.dto.create_entrepreneur.CreateEntrepreneurRequest;
import pl.michal_sobiech.engineering_thesis.dto.create_entrepreneur.CreateEntrepreneurResponse;

@RestController
public class EntrepreneurController {

    @PostMapping("/create_entrepreneur")
    public CreateEntrepreneurResponse createEntrepreneur(@RequestBody CreateEntrepreneurRequest request) {

    }

}
