package pl.michal_sobiech.engineering_thesis.appointment;

import java.net.URI;
import java.net.URL;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import org.SwaggerCodeGenExample.api.AppointmentsApi;
import org.SwaggerCodeGenExample.model.CreateAdyenSessionRequest;
import org.SwaggerCodeGenExample.model.CreateAdyenSessionResponse;
import org.SwaggerCodeGenExample.model.GetCustomerLandingPagePendingAppointmentResponseItem;
import org.SwaggerCodeGenExample.model.GetCustomerLandingPageRejectedAppointmentResponseItem;
import org.SwaggerCodeGenExample.model.GetCustomerLandingPageScheduledAppointmentResponseItem;
import org.SwaggerCodeGenExample.model.RejectPendingAppointmentRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.adyen.model.checkout.CreateCheckoutSessionResponse;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.adyen.AdyenSessionService;
import pl.michal_sobiech.engineering_thesis.appointment.custom.CustomAppointmentQueryService;
import pl.michal_sobiech.engineering_thesis.appointment.custom.CustomAppointmentService;
import pl.michal_sobiech.engineering_thesis.auth.AuthService;
import pl.michal_sobiech.engineering_thesis.customer.Customer;
import pl.michal_sobiech.engineering_thesis.enterprise.Enterprise;
import pl.michal_sobiech.engineering_thesis.enterprise.EnterpriseService;
import pl.michal_sobiech.engineering_thesis.enterprise_member.EnterpriseMember;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceDomain;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceService;
import pl.michal_sobiech.engineering_thesis.exceptions.exceptions.UnauthorizedException;
import pl.michal_sobiech.engineering_thesis.payment.PaymentSubjectType;
import pl.michal_sobiech.engineering_thesis.utils.DateUtils;

@RestController
@RequiredArgsConstructor
public class AppointmentController implements AppointmentsApi {

    private final AuthService authService;
    private final EnterpriseServiceService enterpriseServiceService;
    private final EnterpriseService enterpriseService;
    private final ScheduledAppointmentService scheduledAppointmentService;
    private final AppointmentService appointmentService;
    private final CustomAppointmentQueryService customAppointmentQueryService;
    private final CustomAppointmentService customAppointmentService;
    private final AdyenSessionService adyenService;

    @Override
    public ResponseEntity<List<GetCustomerLandingPagePendingAppointmentResponseItem>> getMyUncancelledFuturePendingAppointments() {
        Customer customer = authService.requireCustomer();

        List<GetCustomerLandingPagePendingAppointmentResponseItem> body = customAppointmentQueryService
                .getCustomerUncancelledFuturePendingAppointments(customer.getUserId())
                .stream()
                .map(appointment -> {
                    EnterpriseServiceDomain service = enterpriseServiceService
                            .getById(appointment.enterpriseServiceId())
                            .orElseThrow();
                    ZoneId timezone = service.timezone();

                    Enterprise enterprise = enterpriseService.getEnterprise(service.enterpriseId()).orElseThrow();

                    String startDatetimeServiceLocal = DateUtils.createIsoLocalDatetime(appointment.startInstant(),
                            timezone);

                    String endDatetimeServiceLocal = DateUtils.createIsoLocalDatetime(appointment.endInstant(),
                            timezone);

                    return new GetCustomerLandingPagePendingAppointmentResponseItem(
                            appointment.appointmentId(),
                            service.enterpriseServiceId(),
                            service.name(),
                            enterprise.name(),
                            appointment.location().getAddress(),
                            startDatetimeServiceLocal,
                            endDatetimeServiceLocal,
                            timezone.toString(),
                            appointment.price(),
                            appointment.currency().toString());
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<List<GetCustomerLandingPageRejectedAppointmentResponseItem>> getMyFutureRejectedAppointments() {
        Customer customer = authService.requireCustomer();

        List<GetCustomerLandingPageRejectedAppointmentResponseItem> body = customAppointmentQueryService
                .getCustomerFutureRejectedAppointments(customer.getUserId())
                .stream()
                .map(appointment -> {
                    EnterpriseServiceDomain service = enterpriseServiceService
                            .getById(appointment.enterpriseServiceId())
                            .orElseThrow();
                    ZoneId timezone = service.timezone();

                    Enterprise enterprise = enterpriseService.getEnterprise(service.enterpriseId())
                            .orElseThrow();

                    String startDatetimeServiceLocal = DateUtils.createIsoLocalDatetime(
                            appointment.startInstant(), timezone);

                    String endDatetimeServiceLocal = DateUtils.createIsoLocalDatetime(
                            appointment.endInstant(), timezone);

                    return new GetCustomerLandingPageRejectedAppointmentResponseItem(
                            appointment.appointmentId(),
                            service.name(),
                            enterprise.name(),
                            appointment.location().getAddress(),
                            startDatetimeServiceLocal,
                            endDatetimeServiceLocal,
                            timezone.toString(),
                            appointment.price(),
                            appointment.currency().toString(),
                            appointment.rejectionMessage());
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<List<GetCustomerLandingPageScheduledAppointmentResponseItem>> getMyUncancelledFutureScheduledAppointments() {
        Customer customer = authService.requireCustomer();

        List<GetCustomerLandingPageScheduledAppointmentResponseItem> body = scheduledAppointmentService
                .getCustomerUncancelledFutureScheduledAppointments(customer.getUserId())
                .stream()
                .map(appointment -> {
                    EnterpriseServiceDomain service = enterpriseServiceService
                            .getById(appointment.enterpriseServiceId())
                            .orElseThrow();
                    ZoneId timezone = service.timezone();

                    Enterprise enterprise = enterpriseService.getEnterprise(service.enterpriseId()).orElseThrow();

                    String startDatetimeServiceLocal = DateUtils.createIsoLocalDatetime(
                            appointment.startInstant(), timezone);

                    String endDatetimeServiceLocal = DateUtils.createIsoLocalDatetime(
                            appointment.endInstant(), timezone);

                    return new GetCustomerLandingPageScheduledAppointmentResponseItem(
                            appointment.appointmentId(),
                            service.enterpriseServiceId(),
                            service.name(),
                            enterprise.name(),
                            service.location().getAddress(),
                            startDatetimeServiceLocal,
                            endDatetimeServiceLocal,
                            timezone.toString(),
                            appointment.price(),
                            appointment.currency().toString());
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<List<GetCustomerLandingPageScheduledAppointmentResponseItem>> getMyPastScheduledAppointments() {
        Customer customer = authService.requireCustomer();

        List<GetCustomerLandingPageScheduledAppointmentResponseItem> body = scheduledAppointmentService
                .getPastScheduledAppointmentsOfCustomer(customer.getUserId())
                .stream()
                .map(appointment -> {
                    EnterpriseServiceDomain service = enterpriseServiceService
                            .getById(appointment.enterpriseServiceId())
                            .orElseThrow();
                    ZoneId timezone = service.timezone();

                    Enterprise enterprise = enterpriseService.getEnterprise(service.enterpriseId()).orElseThrow();

                    String startDatetimeServiceLocal = DateUtils.createIsoLocalDatetime(
                            appointment.startInstant(), timezone);

                    String endDatetimeServiceLocal = DateUtils.createIsoLocalDatetime(
                            appointment.endInstant(), timezone);

                    return new GetCustomerLandingPageScheduledAppointmentResponseItem(
                            appointment.appointmentId(),
                            service.enterpriseServiceId(),
                            service.name(),
                            enterprise.name(),
                            service.location().getAddress(),
                            startDatetimeServiceLocal,
                            endDatetimeServiceLocal,
                            timezone.toString(),
                            appointment.price(),
                            appointment.currency().toString());
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<Void> acceptPendingAppointment(Long appointmentId) {
        EnterpriseMember enterpriseMember = authService.requireEnterpriseMember();

        if (!appointmentService.canUserManageAppointment(enterpriseMember.getUserId(), appointmentId)) {
            throw new UnauthorizedException();
        }

        customAppointmentService.acceptAppointment(appointmentId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> rejectPendingAppointment(Long appointmentId, RejectPendingAppointmentRequest request) {
        // TODO auth everywhere
        EnterpriseMember enterpriseMember = authService.requireEnterpriseMember();

        if (!appointmentService.canUserManageAppointment(enterpriseMember.getUserId(), appointmentId)) {
            throw new UnauthorizedException();
        }

        customAppointmentService.rejectAppointment(appointmentId, request.getRejectionMessage());
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> cancelAppointment(Long appointmentId) {
        scheduledAppointmentService.cancelAppointment(appointmentId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<CreateAdyenSessionResponse> createAdyenSession(
            Long appointmentId,
            CreateAdyenSessionRequest createAdyenSessionRequest) {
        URL returnUrl;
        try {
            returnUrl = URI.create(createAdyenSessionRequest.getReturnUrl()).toURL();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }

        CreateCheckoutSessionResponse createSessionResponse = adyenService.createSession(
                PaymentSubjectType.APPOINTMENT,
                appointmentId,
                returnUrl);

        CreateAdyenSessionResponse body = new CreateAdyenSessionResponse(
                createSessionResponse.getId(),
                createSessionResponse.getSessionData());

        return ResponseEntity.ok(body);
    }

}
