package pl.michal_sobiech.engineering_thesis.appointment.custom;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

import org.SwaggerCodeGenExample.model.Location;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.appointment.AppointmentEntity;
import pl.michal_sobiech.engineering_thesis.appointment.AppointmentRepository;
import pl.michal_sobiech.engineering_thesis.appointment.custom.pending.PendingCustomAppointment;
import pl.michal_sobiech.engineering_thesis.currency_iso.CurrencyIso;
import pl.michal_sobiech.engineering_thesis.enterprise_member.EnterpriseMemberService;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceDomain;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceService;

@Service
@RequiredArgsConstructor
public class CustomAppointmentsService {

    private final EnterpriseServiceService enterpriseServiceService;
    private final AppointmentRepository appointmentRepository;
    private final EnterpriseMemberService enterpriseMemberService;

    public List<ConfirmedCustomAppointment> getConfirmedAppointmentsInDatetimeRange(
            long serviceId,
            Instant from,
            Instant to) {
        List<AppointmentEntity> appointments = appointmentRepository.findConfirmedInDatetimeRange(
                serviceId,
                from.atOffset(ZoneOffset.UTC),
                to.atOffset(ZoneOffset.UTC));
        return appointments.stream().map(a -> ConfirmedCustomAppointment.fromEntity(a)).collect(Collectors.toList());
    }

    public void createCustomAppointment(
            long enterpriseServiceId,
            long customerUserId,
            Instant start,
            Instant end,
            Location location) {

        EnterpriseServiceDomain enterpriseService = enterpriseServiceService.getById(enterpriseServiceId).orElseThrow();

        BigDecimal price = enterpriseService.price();
        CurrencyIso currency = enterpriseService.currency();

        AppointmentEntity appointmentEntity = new AppointmentEntity(
                null,
                enterpriseServiceId,
                customerUserId,
                price,
                currency,
                start.atOffset(ZoneOffset.UTC),
                end.atOffset(ZoneOffset.UTC),
                true,
                null,
                null,
                location.getAddress(),
                location.getLongitude(),
                location.getLatitude(),
                false);
        appointmentRepository.save(appointmentEntity);
    }

    public List<ConfirmedCustomAppointment> getConfirmedCustomAppointmentsOfCustomer(long customerUserId) {
        List<AppointmentEntity> records = appointmentRepository
                .findConfirmedCustomAppointmentsOfCustomer(customerUserId);
        return records.stream().map(record -> ConfirmedCustomAppointment.fromEntity(record))
                .collect(Collectors.toList());
    }

    public List<PendingCustomAppointment> getPendingCustomAppointmentsOfCustomer(long customerUserId) {
        List<AppointmentEntity> records = appointmentRepository.findPendingCustomAppointmentsOfCustomer(customerUserId);
        return records.stream().map(record -> PendingCustomAppointment.fromEntity(record)).collect(Collectors.toList());
    }

    public List<RejectedCustomAppointment> getRejectedCustomAppointmentsOfCustomer(long customerUserId) {
        List<AppointmentEntity> records = appointmentRepository.findPendingCustomAppointmentsOfCustomer(customerUserId);
        return records.stream().map(record -> RejectedCustomAppointment.fromEntity(record))
                .collect(Collectors.toList());
    }

    public void acceptAppointment(long appointmentId) {
        AppointmentEntity appointment = appointmentRepository.findById(appointmentId).orElseThrow();
        appointment.setIsAccepted(true);
        appointmentRepository.save(appointment);
    }

    public void rejectAppointment(long appointmentId, String rejectionMessage) {
        AppointmentEntity appointment = appointmentRepository.findById(appointmentId).orElseThrow();
        appointment.setIsAccepted(false);
        appointment.setRejectionMessage(rejectionMessage);
        appointmentRepository.save(appointment);
    }

}
