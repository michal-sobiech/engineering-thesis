package pl.michal_sobiech.engineering_thesis.appointment.custom;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.SwaggerCodeGenExample.model.Location;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.appointment.AppointmentEntity;
import pl.michal_sobiech.engineering_thesis.appointment.AppointmentRepository;

@Service
@RequiredArgsConstructor
public class CustomAppointmentsService {

    private final AppointmentRepository appointmentRepository;

    public List<ConfirmedCustomAppointment> getConfirmedAppointmentsInDatetimeRange(long serviceId, OffsetDateTime from,
            OffsetDateTime to) {
        List<AppointmentEntity> appointments = appointmentRepository.findConfirmedInDatetimeRange(serviceId, from, to);
        return appointments.stream().map(a -> ConfirmedCustomAppointment.fromEntity(a)).collect(Collectors.toList());
    }

    public void createCustomAppointment(
            long enterpriseServiceId,
            long customerUserId,
            Optional<BigDecimal> price,
            Instant start,
            Instant end,
            Location location) {
        AppointmentEntity appointmentEntity = new AppointmentEntity(
                null,
                enterpriseServiceId,
                customerUserId,
                price.orElse(null),
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
}
