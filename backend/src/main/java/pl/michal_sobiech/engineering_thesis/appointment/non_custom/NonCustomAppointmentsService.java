package pl.michal_sobiech.engineering_thesis.appointment.non_custom;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.appointment.AppointmentEntity;
import pl.michal_sobiech.engineering_thesis.appointment.AppointmentRepository;
import pl.michal_sobiech.engineering_thesis.utils.DateUtils;

@Service
@RequiredArgsConstructor
public class NonCustomAppointmentsService {

    private final AppointmentRepository appointmentRepository;

    public List<NonCustomAppointment> getAllByServiceIdAndDatetimeRange(long serviceId, OffsetDateTime from,
            OffsetDateTime to) {
        List<AppointmentEntity> records = appointmentRepository.findAllInRange(serviceId, from, to);
        return records.stream().map(record -> NonCustomAppointment.fromEntity(record)).collect(Collectors.toList());
    }

    public List<NonCustomAppointment> getAllByServiceIdAndDate(long serviceId, OffsetDateTime date) {
        OffsetDateTime from = DateUtils.createOffsetDateTimeWithResetTime(date);
        OffsetDateTime to = from.plusDays(1);
        return getAllByServiceIdAndDatetimeRange(serviceId, from, to);
    }

    public void createNonCustomAppointment(
            long enterpriseServiceId,
            long customerUserId,
            Optional<BigDecimal> price,
            Instant start,
            Instant end) {
        AppointmentEntity appointmentEntity = new AppointmentEntity(
                null,
                enterpriseServiceId,
                customerUserId,
                price.orElse(null),
                start.atOffset(ZoneOffset.UTC),
                end.atOffset(ZoneOffset.UTC),
                false,
                null,
                null,
                null,
                null,
                null);
        appointmentRepository.save(appointmentEntity);
    }

}
