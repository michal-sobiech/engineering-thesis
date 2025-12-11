package pl.michal_sobiech.payout_worker.enterprise_service;

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
import pl.michal_sobiech.core.enterprise_service_availability_template.EnterpriseServiceAvailabilityTemplateService;
import pl.michal_sobiech.core.enterprise_service_availability_template.non_custom.NonCustomEnterpriseServiceAvailabilityTemplateService;
import pl.michal_sobiech.core.enterprise_service_slot_template.EnterpriseServiceSlotTemplateRepository;
import pl.michal_sobiech.core.enterprise_service_slot_template.EnterpriseServiceSlotTemplateService;
import pl.michal_sobiech.core.enterprise_service_slot_template.custom_appointments.CustomAppointmentsEnterpriseServiceTimeWindowTemplateService;
import pl.michal_sobiech.core.enterprise_service_slot_template.non_custom_appointments.NonCustomAppointmentsEnterpriseServiceSlotTemplateService;

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
            CustomAppointmentsEnterpriseServiceTimeWindowTemplateService customAppointmentsEnterpriseServiceTimeWindowTemplateService) {
        return new CustomAppointmentsEnterpriseServiceService(enterpriseServiceRepository,
                customAppointmentsEnterpriseServiceTimeWindowTemplateService);
    }

    @Bean
    public NonCustomAppointmentsEnterpriseServiceService nonCustomAppointmentsEnterpriseServiceService(
            EnterpriseServiceRepository enterpriseServiceRepository,
            NonCustomAppointmentsEnterpriseServiceSlotTemplateService nonCustomAppointmentsEnterpriseServiceSlotTemplateService) {
        return new NonCustomAppointmentsEnterpriseServiceService(enterpriseServiceRepository,
                nonCustomAppointmentsEnterpriseServiceSlotTemplateService);
    }

    @Bean
    public NonCustomEnterpriseServiceAvailabilityService nonCustomEnterpriseServiceAvailabilityService(
            NonCustomAppointmentsService nonCustomAppointmentsService,
            EnterpriseServiceService enterpriseServiceService,
            EnterpriseServiceAvailabilityTemplateService enterpriseServiceAvailabilityTemplateService) {
        return new NonCustomEnterpriseServiceAvailabilityService(nonCustomAppointmentsService, enterpriseServiceService,
                enterpriseServiceAvailabilityTemplateService);
    }

    @Bean
    public EnterpriseServiceAvailabilityTemplateService enterpriseServiceAvailabilityTemplateService(
            EnterpriseServiceSlotTemplateService enterpriseServiceSlotTemplateService) {
        return new EnterpriseServiceAvailabilityTemplateService(enterpriseServiceSlotTemplateService);
    }

    @Bean
    public CustomEnterpriseServiceAvailabilityService customEnterpriseServiceAvailabilityService(
            EnterpriseServiceService enterpriseServiceService,
            CustomAppointmentsEnterpriseServiceTimeWindowTemplateService customAppointmentsEnterpriseServiceTimeWindowTemplateService,
            CustomAppointmentService customAppointmentService) {
        return new CustomEnterpriseServiceAvailabilityService(enterpriseServiceService,
                customAppointmentsEnterpriseServiceTimeWindowTemplateService,
                customAppointmentService);
    }

    @Bean
    public NonCustomEnterpriseServiceAvailabilityTemplateService nonCustomEnterpriseServiceAvailabilityTemplateService(
            EnterpriseServiceSlotTemplateService enterpriseServiceSlotTemplateService) {
        return new NonCustomEnterpriseServiceAvailabilityTemplateService(enterpriseServiceSlotTemplateService);
    }

    @Bean
    public EnterpriseServiceSlotTemplateService enterpriseServiceSlotTemplateService(
            EnterpriseServiceSlotTemplateRepository enterpriseServiceSlotTemplateRepository) {
        return new EnterpriseServiceSlotTemplateService(enterpriseServiceSlotTemplateRepository);
    }

    @Bean
    public CustomAppointmentsEnterpriseServiceTimeWindowTemplateService customAppointmentsEnterpriseServiceTimeWindowTemplateService(
            EnterpriseServiceSlotTemplateService enterpriseServiceSlotTemplateService,
            EnterpriseServiceSlotTemplateRepository enterpriseServiceSlotTemplateRepository) {
        return new CustomAppointmentsEnterpriseServiceTimeWindowTemplateService(
                enterpriseServiceSlotTemplateService,
                enterpriseServiceSlotTemplateRepository);
    }

    @Bean
    public NonCustomAppointmentsEnterpriseServiceSlotTemplateService nonCustomAppointmentsEnterpriseServiceSlotTemplateService(
            EnterpriseServiceSlotTemplateRepository enterpriseServiceSlotTemplateRepository) {
        return new NonCustomAppointmentsEnterpriseServiceSlotTemplateService(enterpriseServiceSlotTemplateRepository);
    }

}
