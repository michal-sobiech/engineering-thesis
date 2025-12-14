package pl.michal_sobiech.engineering_thesis.enterprise_service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pl.michal_sobiech.core.appointment.custom.CustomAppointmentService;
import pl.michal_sobiech.core.appointment.non_custom.NonCustomAppointmentsService;
import pl.michal_sobiech.core.enteprise_service_search.EnterpriseServiceSearchService;
import pl.michal_sobiech.core.enterprise_service.EnterpriseServiceRepository;
import pl.michal_sobiech.core.enterprise_service.EnterpriseServiceService;
import pl.michal_sobiech.core.enterprise_service.custom_appointments.CustomAppointmentsEnterpriseServiceService;
import pl.michal_sobiech.core.enterprise_service.no_custom_appointments.NonCustomAppointmentsEnterpriseServiceService;
import pl.michal_sobiech.core.enterprise_service_availability.CustomEnterpriseServiceAvailabilityService;
import pl.michal_sobiech.core.enterprise_service_availability.NonCustomEnterpriseServiceAvailabilityService;
import pl.michal_sobiech.core.enterprise_service_default_availability.custom.CustomEnterpriseServiceDefaultAvailabilityService;
import pl.michal_sobiech.core.enterprise_service_default_availability.non_custom.NonCustomEnterpriseServiceDefaultAvailabilityService;
import pl.michal_sobiech.core.enterprise_service_slot_template.EnterpriseServiceSlotTemplateRepository;
import pl.michal_sobiech.core.enterprise_service_slot_template.EnterpriseServiceSlotTemplateService;
import pl.michal_sobiech.core.enterprise_service_slot_template.custom_appointments.CustomTimeWindowTemplateService;
import pl.michal_sobiech.core.enterprise_service_slot_template.non_custom_appointments.NonCustomSlotTemplateService;

@Configuration
public class EnterpriseServiceConfig {

    @Bean
    public EnterpriseServiceSearchService enterpriseServiceSearchService(
            EnterpriseServiceRepository enterpriseServiceRepository) {
        return new EnterpriseServiceSearchService(enterpriseServiceRepository);
    }

    @Bean
    public EnterpriseServiceService enterpriseServiceService(
            EnterpriseServiceRepository enterpriseServiceRepository,
            NonCustomAppointmentsEnterpriseServiceService nonCustomAppointmentsEnterpriseServiceService,
            CustomAppointmentsEnterpriseServiceService customAppointmentsEnterpriseServiceService) {
        return new EnterpriseServiceService(enterpriseServiceRepository, nonCustomAppointmentsEnterpriseServiceService,
                customAppointmentsEnterpriseServiceService);
    }

    @Bean
    public CustomAppointmentsEnterpriseServiceService customAppointmentsEnterpriseServiceService(
            EnterpriseServiceRepository enterpriseServiceRepository,
            CustomTimeWindowTemplateService customAppointmentsEnterpriseServiceTimeWindowTemplateService) {
        return new CustomAppointmentsEnterpriseServiceService(enterpriseServiceRepository,
                customAppointmentsEnterpriseServiceTimeWindowTemplateService);
    }

    @Bean
    public NonCustomAppointmentsEnterpriseServiceService nonCustomAppointmentsEnterpriseServiceService(
            EnterpriseServiceRepository enterpriseServiceRepository,
            NonCustomSlotTemplateService nonCustomAppointmentsEnterpriseServiceSlotTemplateService) {
        return new NonCustomAppointmentsEnterpriseServiceService(enterpriseServiceRepository,
                nonCustomAppointmentsEnterpriseServiceSlotTemplateService);
    }

    @Bean
    public NonCustomEnterpriseServiceAvailabilityService nonCustomEnterpriseServiceAvailabilityService(
            NonCustomAppointmentsService nonCustomAppointmentsService,
            NonCustomEnterpriseServiceDefaultAvailabilityService defaultAvailabilityService) {
        return new NonCustomEnterpriseServiceAvailabilityService(
                nonCustomAppointmentsService,
                defaultAvailabilityService);
    }

    @Bean
    public CustomEnterpriseServiceAvailabilityService customEnterpriseServiceAvailabilityService(
            CustomAppointmentService customAppointmentService,
            CustomEnterpriseServiceDefaultAvailabilityService defaultAvailabilityService) {
        return new CustomEnterpriseServiceAvailabilityService(customAppointmentService, defaultAvailabilityService);
    }

    @Bean
    public NonCustomEnterpriseServiceDefaultAvailabilityService nonCustomEnterpriseServiceAvailabilityTemplateService(
            EnterpriseServiceSlotTemplateService enterpriseServiceSlotTemplateService,
            EnterpriseServiceService enterpriseServiceService) {
        return new NonCustomEnterpriseServiceDefaultAvailabilityService(
                enterpriseServiceSlotTemplateService, enterpriseServiceService);
    }

    @Bean
    public CustomEnterpriseServiceDefaultAvailabilityService customEnterpriseServiceDefaultAvailabilityService(
            EnterpriseServiceSlotTemplateService enterpriseServiceSlotTemplateService,
            EnterpriseServiceService enterpriseServiceService) {
        return new CustomEnterpriseServiceDefaultAvailabilityService(
                enterpriseServiceSlotTemplateService, enterpriseServiceService);
    }

    @Bean
    public EnterpriseServiceSlotTemplateService enterpriseServiceSlotTemplateService(
            EnterpriseServiceSlotTemplateRepository enterpriseServiceSlotTemplateRepository) {
        return new EnterpriseServiceSlotTemplateService(enterpriseServiceSlotTemplateRepository);
    }

    @Bean
    public CustomTimeWindowTemplateService customAppointmentsEnterpriseServiceTimeWindowTemplateService(
            EnterpriseServiceSlotTemplateService enterpriseServiceSlotTemplateService,
            EnterpriseServiceSlotTemplateRepository enterpriseServiceSlotTemplateRepository) {
        return new CustomTimeWindowTemplateService(
                enterpriseServiceSlotTemplateService,
                enterpriseServiceSlotTemplateRepository);
    }

    @Bean
    public NonCustomSlotTemplateService nonCustomAppointmentsEnterpriseServiceSlotTemplateService(
            EnterpriseServiceSlotTemplateRepository enterpriseServiceSlotTemplateRepository) {
        return new NonCustomSlotTemplateService(enterpriseServiceSlotTemplateRepository);
    }

}
