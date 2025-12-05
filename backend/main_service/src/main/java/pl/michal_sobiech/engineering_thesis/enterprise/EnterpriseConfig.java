package pl.michal_sobiech.engineering_thesis.enterprise;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pl.michal_sobiech.core.available_enterprise_service_search.AvailableEnterpriseServiceSearchService;
import pl.michal_sobiech.core.available_enterprise_service_search.custom.AvailableCustomEnterpriseServiceSearchService;
import pl.michal_sobiech.core.available_enterprise_service_search.non_custom.AvailableNonCustomEnterpriseServiceSearchService;
import pl.michal_sobiech.core.available_enterprise_service_time_windows_search.custom.AvailableCustomEnterpriseServiceFreeTimeWindowSearchService;
import pl.michal_sobiech.core.available_enterprise_service_time_windows_search.non_custom.AvailableNonCustomEnterpriseServiceTimeWindowsSearchService;
import pl.michal_sobiech.core.enteprise_service_search.EnterpriseServiceSearchService;
import pl.michal_sobiech.core.enterprise.EnterpriseRepository;
import pl.michal_sobiech.core.enterprise.EnterpriseService;
import pl.michal_sobiech.core.enterprise_service_availability.CustomEnterpriseServiceAvailabilityService;
import pl.michal_sobiech.core.enterprise_service_availability.NonCustomEnterpriseServiceAvailabilityService;

@Configuration
public class EnterpriseConfig {

    @Bean
    public AvailableEnterpriseServiceSearchService availableEnterpriseServiceSearchService(
            AvailableNonCustomEnterpriseServiceSearchService availableNonCustomEnterpriseServiceSearchService,
            AvailableCustomEnterpriseServiceSearchService availableCustomEnterpriseServiceSearchService) {
        return new AvailableEnterpriseServiceSearchService(availableNonCustomEnterpriseServiceSearchService,
                availableCustomEnterpriseServiceSearchService);
    }

    @Bean
    public AvailableCustomEnterpriseServiceSearchService availableCustomEnterpriseServiceSearchService(
            AvailableCustomEnterpriseServiceFreeTimeWindowSearchService availableCustomEnterpriseServiceFreeTimeWindowSearchService) {
        return new AvailableCustomEnterpriseServiceSearchService(
                availableCustomEnterpriseServiceFreeTimeWindowSearchService);
    }

    @Bean
    public AvailableNonCustomEnterpriseServiceSearchService availableNonCustomEnterpriseServiceSearchService(
            AvailableNonCustomEnterpriseServiceTimeWindowsSearchService timeWindowsSearchService) {
        return new AvailableNonCustomEnterpriseServiceSearchService(timeWindowsSearchService);
    }

    @Bean
    public AvailableCustomEnterpriseServiceFreeTimeWindowSearchService availableCustomEnterpriseServiceFreeTimeWindowSearchService(
            CustomEnterpriseServiceAvailabilityService customEnterpriseServiceAvailabilityService,
            EnterpriseServiceSearchService enterpriseServiceSearchService) {
        return new AvailableCustomEnterpriseServiceFreeTimeWindowSearchService(
                customEnterpriseServiceAvailabilityService,
                enterpriseServiceSearchService);
    }

    @Bean
    public AvailableNonCustomEnterpriseServiceTimeWindowsSearchService availableNonCustomEnterpriseServiceTimeWindowsSearchService(
            EnterpriseServiceSearchService enterpriseServiceSearchService,
            NonCustomEnterpriseServiceAvailabilityService nonCustomEnterpriseServiceAvailabilityService) {
        return new AvailableNonCustomEnterpriseServiceTimeWindowsSearchService(enterpriseServiceSearchService,
                nonCustomEnterpriseServiceAvailabilityService);
    }

    @Bean
    public EnterpriseService enterpriseService(EnterpriseRepository enterpriseRepository) {
        return new EnterpriseService(enterpriseRepository);
    }

}
