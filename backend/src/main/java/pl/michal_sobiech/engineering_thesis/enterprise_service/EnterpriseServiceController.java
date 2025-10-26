package pl.michal_sobiech.engineering_thesis.enterprise_service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import org.SwaggerCodeGenExample.api.ServicesApi;
import org.SwaggerCodeGenExample.model.ServiceSearchResponseItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.enterprise_service_search.SearchEnterpriseServicesCommand;

@RestController
@RequiredArgsConstructor
public class EnterpriseServiceController implements ServicesApi {

    private final EnterpriseServiceService enterpriseServiceService;

    @Override
    public ResponseEntity<List<ServiceSearchResponseItem>> searchServices(
            @Nullable String serviceName,
            @Nullable String enterpriseName,
            @Nullable OffsetDateTime startDate,
            @Nullable OffsetDateTime endDate,
            @Nullable BigDecimal preferredLongitude,
            @Nullable BigDecimal preferredLatitude,
            @Nullable String cathegory) {
        var command = SearchEnterpriseServicesCommand.fromRaw(serviceName, enterpriseName, startDate, endDate,
                preferredLongitude, preferredLatitude, cathegory);
        List<ServiceSearchResponseItem> 
    }

}
