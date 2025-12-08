package pl.michal_sobiech.payout_worker.appointment;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pl.michal_sobiech.core.appointment.AppointmentRepository;
import pl.michal_sobiech.core.appointment.AppointmentService;
import pl.michal_sobiech.core.appointment.AppointmentWithDetailsService;
import pl.michal_sobiech.core.appointment.custom.CustomAppointmentQueryService;
import pl.michal_sobiech.core.appointment.custom.CustomAppointmentService;
import pl.michal_sobiech.core.appointment.custom.CustomAppointmentWithDetailsService;
import pl.michal_sobiech.core.appointment.non_custom.NonCustomAppointmentQueryService;
import pl.michal_sobiech.core.appointment.non_custom.NonCustomAppointmentWithDetailsService;
import pl.michal_sobiech.core.appointment.non_custom.NonCustomAppointmentsService;
import pl.michal_sobiech.core.customer.CustomerService;
import pl.michal_sobiech.core.enterprise_member.EnterpriseMemberService;
import pl.michal_sobiech.core.enterprise_service.EnterpriseServiceService;

@Configuration
public class AppointmentConfig {

    @Bean
    public AppointmentService appointmentService(
            EnterpriseMemberService enterpriseMemberService,
            NonCustomAppointmentQueryService nonCustomAppointmentQueryService,
            CustomAppointmentQueryService customAppointmentQueryService,
            AppointmentRepository appointmentRepository) {
        return new AppointmentService(enterpriseMemberService, nonCustomAppointmentQueryService,
                customAppointmentQueryService, appointmentRepository);
    }

    @Bean
    public AppointmentWithDetailsService appointmentWithDetailsService(
            AppointmentService appointmentService,
            CustomerService customerService) {
        return new AppointmentWithDetailsService(appointmentService, customerService);
    }

    @Bean
    public CustomAppointmentQueryService customAppointmentQueryService(
            AppointmentRepository appointmentRepository) {
        return new CustomAppointmentQueryService(appointmentRepository);
    }

    @Bean
    public CustomAppointmentService customAppointmentService(
            EnterpriseServiceService enterpriseServiceService,
            AppointmentRepository appointmentRepository) {
        return new CustomAppointmentService(enterpriseServiceService, appointmentRepository);
    }

    @Bean
    public CustomAppointmentWithDetailsService customAppointmentWithDetailsService(
            CustomAppointmentQueryService customAppointmentQueryService,
            CustomerService customerService) {
        return new CustomAppointmentWithDetailsService(customAppointmentQueryService, customerService);
    }

    @Bean
    public NonCustomAppointmentQueryService nonCustomAppointmentQueryService(
            AppointmentRepository appointmentRepository) {
        return new NonCustomAppointmentQueryService(appointmentRepository);
    }

    @Bean
    public NonCustomAppointmentsService nonCustomAppointmentsService(
            EnterpriseServiceService enterpriseServiceService,
            AppointmentRepository appointmentRepository) {
        return new NonCustomAppointmentsService(enterpriseServiceService, appointmentRepository);
    }

    @Bean
    public NonCustomAppointmentWithDetailsService nonCustomAppointmentWithDetailsService(
            AppointmentRepository appointmentRepository) {
        return new NonCustomAppointmentWithDetailsService(appointmentRepository);
    }

}