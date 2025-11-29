package pl.michal_sobiech.engineering_thesis.adyen;

import org.SwaggerCodeGenExample.api.AdyenApi;
import org.SwaggerCodeGenExample.model.AdyenWebhookRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AdyenController implements AdyenApi {

    private final AdyenWebhookService adyenWebhookService;

    @Override
    public ResponseEntity<Void> sendAdyenPaymentStatus(AdyenWebhookRequest request) {
        // TODO verify this request

        adyenWebhookService.handleWebhook(request);

        return ResponseEntity.ok().build();
    }

}
