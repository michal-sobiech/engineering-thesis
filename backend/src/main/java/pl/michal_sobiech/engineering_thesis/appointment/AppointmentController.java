package pl.michal_sobiech.engineering_thesis.appointment;

import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import org.SwaggerCodeGenExample.api.AppointmentsApi;
import org.SwaggerCodeGenExample.model.GetCustomerLandingPagePendingAppointmentResponseItem;
import org.SwaggerCodeGenExample.model.GetCustomerLandingPageRejectedAppointmentResponseItem;
import org.SwaggerCodeGenExample.model.GetCustomerLandingPageScheduledAppointmentResponseItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.appointment.custom.CustomAppointmentsService;
import pl.michal_sobiech.engineering_thesis.appointment.custom.PendingCustomAppointment;
import pl.michal_sobiech.engineering_thesis.appointment.custom.RejectedCustomAppointment;
import pl.michal_sobiech.engineering_thesis.appointment.non_custom.NonCustomAppointmentsService;
import pl.michal_sobiech.engineering_thesis.auth.AuthService;
import pl.michal_sobiech.engineering_thesis.customer.Customer;
import pl.michal_sobiech.engineering_thesis.enterprise.EnterpriseEntity;
import pl.michal_sobiech.engineering_thesis.enterprise.EnterpriseService;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceEntity;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceService;
import pl.michal_sobiech.engineering_thesis.utils.DateUtils;

@RestController
@RequiredArgsConstructor
public class AppointmentController implements AppointmentsApi {

    private final AuthService authService;
    private final NonCustomAppointmentsService nonCustomAppointmentsService;
    private final CustomAppointmentsService customAppointmentsService;
    private final EnterpriseServiceService enterpriseServiceService;
    private final EnterpriseService enterpriseService;
    private final ScheduledAppointmentService scheduledAppointmentService;

    @Override
    public ResponseEntity<List<GetCustomerLandingPagePendingAppointmentResponseItem>> getMyPendingAppointments() {
        Customer customer = authService.requireCustomer();
        List<PendingCustomAppointment> appointments = customAppointmentsService
                .getPendingCustomAppointmentsOfCustomer(customer.getUserId());

        List<GetCustomerLandingPagePendingAppointmentResponseItem> body = appointments.stream()
                .map(appointment -> {
                    EnterpriseServiceEntity service = enterpriseServiceService
                            .findById(appointment.enterpriseServiceId()).orElseThrow();
                    ZoneId timezone = service.getTimeZone();

                    EnterpriseEntity enterprise = enterpriseService
                            .getEnterprise(service.getEnterpriseId())
                            .orElseThrow();

                    String startDatetimeServiceLocal = DateUtils.createIsoLocalDatetime(
                            appointment.startInstant(), timezone);

                    String endDatetimeServiceLocal = DateUtils.createIsoLocalDatetime(
                            appointment.endInstant(), timezone);

                    return new GetCustomerLandingPagePendingAppointmentResponseItem(
                            service.getName(),
                            enterprise.getName(),
                            appointment.location().getAddress(),
                            startDatetimeServiceLocal,
                            endDatetimeServiceLocal,
                            timezone.toString(),
                            appointment.price());
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<List<GetCustomerLandingPageRejectedAppointmentResponseItem>> getMyRejectedAppointments() {
        Customer customer = authService.requireCustomer();
        List<RejectedCustomAppointment> appointments = customAppointmentsService
                .getRejectedCustomAppointmentsOfCustomer(customer.getUserId());

        List<GetCustomerLandingPageRejectedAppointmentResponseItem> body = appointments.stream()
                .map(appointment -> {
                    EnterpriseServiceEntity service = enterpriseServiceService
                            .findById(appointment.enterpriseServiceId()).orElseThrow();
                    ZoneId timezone = service.getTimeZone();

                    EnterpriseEntity enterprise = enterpriseService
                            .getEnterprise(service.getEnterpriseId())
                            .orElseThrow();

                    String startDatetimeServiceLocal = DateUtils.createIsoLocalDatetime(
                            appointment.startInstant(), timezone);

                    String endDatetimeServiceLocal = DateUtils.createIsoLocalDatetime(
                            appointment.endInstant(), timezone);

                    return new GetCustomerLandingPageRejectedAppointmentResponseItem(
                            service.getName(),
                            enterprise.getName(),
                            appointment.location().getAddress(),
                            startDatetimeServiceLocal,
                            endDatetimeServiceLocal,
                            timezone.toString(),
                            appointment.price(),
                            appointment.rejectionMessage());
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<List<GetCustomerLandingPageScheduledAppointmentResponseItem>> getMyFutureScheduledAppointments() {
        Customer customer = authService.requireCustomer();

        List<ScheduledAppointment> appointments = scheduledAppointmentService
                .getFutureScheduledAppointmentsOfCustomer(customer.getUserId());

        List<GetCustomerLandingPageScheduledAppointmentResponseItem> body = appointments.stream()
                .map(appointment -> {
                    EnterpriseServiceEntity service = enterpriseServiceService
                            .findById(appointment.enterpriseServiceId()).orElseThrow();
                    ZoneId timezone = service.getTimeZone();

                    EnterpriseEntity enterprise = enterpriseService
                            .getEnterprise(service.getEnterpriseId())
                            .orElseThrow();

                    String startDatetimeServiceLocal = DateUtils.createIsoLocalDatetime(
                            appointment.startInstant(), timezone);

                    String endDatetimeServiceLocal = DateUtils.createIsoLocalDatetime(
                            appointment.endInstant(), timezone);

                    return new GetCustomerLandingPageScheduledAppointmentResponseItem(
                            service.getName(),
                            enterprise.getName(),
                            service.getAddress(),
                            startDatetimeServiceLocal,
                            endDatetimeServiceLocal,
                            timezone.toString(),
                            appointment.price());
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<List<GetCustomerLandingPageScheduledAppointmentResponseItem>> getMyPastScheduledAppointments() {
        Customer customer = authService.requireCustomer();

        List<ScheduledAppointment> appointments = scheduledAppointmentService
                .getPastScheduledAppointmentsOfCustomer(customer.getUserId());

        List<GetCustomerLandingPageScheduledAppointmentResponseItem> body = appointments.stream()
                .map(appointment -> {
                    EnterpriseServiceEntity service = enterpriseServiceService
                            .findById(appointment.enterpriseServiceId()).orElseThrow();
                    ZoneId timezone = service.getTimeZone();

                    EnterpriseEntity enterprise = enterpriseService
                            .getEnterprise(service.getEnterpriseId())
                            .orElseThrow();

                    String startDatetimeServiceLocal = DateUtils.createIsoLocalDatetime(
                            appointment.startInstant(), timezone);

                    String endDatetimeServiceLocal = DateUtils.createIsoLocalDatetime(
                            appointment.endInstant(), timezone);

                    return new GetCustomerLandingPageScheduledAppointmentResponseItem(
                            service.getName(),
                            enterprise.getName(),
                            service.getAddress(),
                            startDatetimeServiceLocal,
                            endDatetimeServiceLocal,
                            timezone.toString(),
                            appointment.price());
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(body);
    }
}
