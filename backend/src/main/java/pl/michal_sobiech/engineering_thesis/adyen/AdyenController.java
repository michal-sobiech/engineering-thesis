package pl.michal_sobiech.engineering_thesis.adyen;

import org.SwaggerCodeGenExample.api.AdyenApi;
import org.SwaggerCodeGenExample.model.SendAdyenSessionResultRequest;
import org.SwaggerCodeGenExample.model.SendAdyenSessionResultResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AdyenController implements AdyenApi {

    private final AdyenSessionService adyenSessionService;

    @Override
    public ResponseEntity<SendAdyenSessionResultResponse> sendAdyenSessionResult(
            SendAdyenSessionResultRequest request) {
        boolean isSessionSuccessful = adyenSessionService.fetchIsSessionSuccessful(
                request.getSessionId(),
                request.getSessionResultToken());

        var body = new SendAdyenSessionResultResponse(isSessionSuccessful);
        return ResponseEntity.ok(body);
    }

}
