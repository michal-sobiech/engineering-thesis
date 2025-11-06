package pl.michal_sobiech.engineering_thesis.appointment.custom;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

import org.SwaggerCodeGenExample.model.Location;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.appointment.AppointmentEntity;
import pl.michal_sobiech.engineering_thesis.appointment.AppointmentRepository;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceEntity;
import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceService;

@Service
@RequiredArgsConstructor
public class CustomAppointmentsService {

    private final EnterpriseServiceService enterpriseServiceService;
    private final AppointmentRepository appointmentRepository;

    public List<ConfirmedCustomAppointment> getConfirmedAppointmentsInDatetimeRange(long serviceId, OffsetDateTime from,
            OffsetDateTime to) {
        List<AppointmentEntity> appointments = appointmentRepository.findConfirmedInDatetimeRange(serviceId, from, to);
        return appointments.stream().map(a -> ConfirmedCustomAppointment.fromEntity(a)).collect(Collectors.toList());
    }

    public void createCustomAppointment(
            long enterpriseServiceId,
            long customerUserId,
            Instant start,
            Instant end,
            Location location) {

        EnterpriseServiceEntity enterpriseService = enterpriseServiceService.findById(enterpriseServiceId)
                .orElseThrow();

        BigDecimal price = enterpriseService.getPrice();

        AppointmentEntity appointmentEntity = new AppointmentEntity(
                null,
                enterpriseServiceId,
                customerUserId,
                price,
                start.atOffset(ZoneOffset.UTC),
                end.atOffset(ZoneOffset.UTC),
                true,
                null,
                null,
                location.getAddress(),
                location.getLongitude(),
                location.getLatitude());
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
}
