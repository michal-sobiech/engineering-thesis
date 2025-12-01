package pl.michal_sobiech.engineering_thesis.health_check;

import org.SwaggerCodeGenExample.api.HealthCheckApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController implements HealthCheckApi {

    @Override
    public ResponseEntity<Void> checkHealth() {
        return ResponseEntity.ok().build();
    }

}
