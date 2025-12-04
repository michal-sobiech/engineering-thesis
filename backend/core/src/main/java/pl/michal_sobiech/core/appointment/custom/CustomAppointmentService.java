package pl.michal_sobiech.shared.appointment.custom;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.SwaggerCodeGenExample.model.Location;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.currency_iso.CurrencyIso;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceDomain;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceService;
import pl.michal_sobiech.shared.appointment.AppointmentEntity;
import pl.michal_sobiech.shared.appointment.AppointmentRepository;
import pl.michal_sobiech.shared.appointment.UncancelledScheduledAppointment;

@Service
@RequiredArgsConstructor
public class CustomAppointmentService {

    private final EnterpriseServiceService enterpriseServiceService;
    private final AppointmentRepository appointmentRepository;

    // TODO add uncancelled to method name?
    public List<UncancelledScheduledAppointment> getConfirmedAppointmentsInDatetimeRange(
            long serviceId,
            Instant from,
            Instant to) {
        List<AppointmentEntity> appointments = appointmentRepository.findConfirmedInDatetimeRange(
                serviceId,
                from.atOffset(ZoneOffset.UTC),
                to.atOffset(ZoneOffset.UTC));
        return appointments
                .stream()
                .map(UncancelledScheduledAppointment::fromEntity)
                .map(Optional::orElseThrow)
                .collect(Collectors.toList());
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
                false,
                false,
                null,
                null,
                null);
        appointmentRepository.save(appointmentEntity);
    }

    public List<UncancelledScheduledAppointment> getConfirmedCustomAppointmentsOfCustomer(long customerUserId) {
        List<AppointmentEntity> records = appointmentRepository
                .findConfirmedCustomAppointmentsOfCustomer(customerUserId);
        return records
                .stream()
                .map(UncancelledScheduledAppointment::fromEntity)
                .map(Optional::orElseThrow)
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
