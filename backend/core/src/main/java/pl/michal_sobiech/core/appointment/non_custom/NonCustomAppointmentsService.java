package pl.michal_sobiech.core.appointment.non_custom;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.appointment.AppointmentEntity;
import pl.michal_sobiech.core.appointment.AppointmentRepository;
import pl.michal_sobiech.core.currency_iso.CurrencyIso;
import pl.michal_sobiech.core.enterprise_service.EnterpriseServiceDomain;
import pl.michal_sobiech.core.enterprise_service.EnterpriseServiceService;
import pl.michal_sobiech.core.location.Location;
import pl.michal_sobiech.core.utils.DateUtils;

@RequiredArgsConstructor
public class NonCustomAppointmentsService {

    private final EnterpriseServiceService enterpriseServiceService;
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
            Instant start,
            Instant end) {

        // TODO check if appointment can be made

        EnterpriseServiceDomain enterpriseService = enterpriseServiceService.getById(enterpriseServiceId).orElseThrow();

        Location location = enterpriseService.location();

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
                false,
                null,
                null,
                location.address(),
                location.latitude(),
                location.longitude(),
                false,
                false,
                null,
                null,
                null);
        appointmentRepository.save(appointmentEntity);
    }

    public List<NonCustomAppointment> getNonCustomAppointmentsOfCustomer(long customerUserId) {
        List<AppointmentEntity> records = appointmentRepository.findNonCustomAppointmentsOfCustomer(customerUserId);
        return records.stream().map(record -> NonCustomAppointment.fromEntity(record)).collect(Collectors.toList());
    }

}
