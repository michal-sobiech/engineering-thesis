package pl.michal_sobiech.engineering_thesis.enterprise_service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.SwaggerCodeGenExample.api.ServicesApi;
import org.SwaggerCodeGenExample.model.ServiceSearchResponseItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.enterprise_service_search.EnterpriseServiceSearchService;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot.EnterpriseServiceSlotService;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot.ServiceSearchSlot;
import pl.michal_sobiech.engineering_thesis.utils.HttpUtils;

@RestController
@RequiredArgsConstructor
public class EnterpriseServiceController implements ServicesApi {

    private static final double MAX_VALUE_OF_MAX_DISTANCE_KM = 100;

    private final EnterpriseServiceCathegoryConverter enterpriseServiceCathegoryConverter;
    private final EnterpriseServiceSearchService enterpriseServiceSearchService;
    private final EnterpriseServiceSlotService enterpriseServiceSlotService;

    @Override
    public ResponseEntity<List<ServiceSearchResponseItem>> searchServices(
            Double preferredLongitude,
            Double preferredLatitude,
            String cathegory,
            Double maxDistanceKm,
            @Nullable String serviceName,
            @Nullable String enterpriseName,
            @Nullable OffsetDateTime startDate,
            @Nullable OffsetDateTime endDate) {
        if (maxDistanceKm > MAX_VALUE_OF_MAX_DISTANCE_KM) {
            return HttpUtils.createBadRequestResponse();
        }

        EnterpriseServiceCathegory cathegoryParsed = enterpriseServiceCathegoryConverter
                .convertToEntityAttribute(cathegory);
        if (cathegoryParsed == null) {
            return HttpUtils.createBadRequestResponse();
        }

        List<ServiceSearchSlot> filteredSlots = enterpriseServiceSearchService.searchNoCustomAppointmentsSlots(
                Optional.of(serviceName),
                Optional.of(enterpriseName),
                Optional.of(startDate),
                Optional.of(endDate),
                cathegoryParsed,
                preferredLongitude,
                preferredLatitude,
                maxDistanceKm);
        var body = filteredSlots.stream().map(slot -> new ServiceSearchResponseItem(
                slot.getServiceName(),
                slot.getEnterpriseName(),
                slot.getAddress())).toList();
        return ResponseEntity.ok(body);
    }
}
