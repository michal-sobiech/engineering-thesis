package pl.michal_sobiech.core.enterprise_service;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.enterprise_service.custom_appointments.CustomAppointmentsEnterpriseServiceService;
import pl.michal_sobiech.core.enterprise_service.no_custom_appointments.NonCustomAppointmentsEnterpriseServiceService;
import pl.michal_sobiech.core.time_slot.TimeSlot;
import pl.michal_sobiech.core.time_slot.TimeSlotWithOccupancy;

@RequiredArgsConstructor
public class EnterpriseServiceService {

    private final EnterpriseServiceRepository enterpriseServiceRepository;
    private final NonCustomAppointmentsEnterpriseServiceService nonCustomAppointmentsEnterpriseServiceService;
    private final CustomAppointmentsEnterpriseServiceService customAppointmentsEnterpriseServiceService;

    public Optional<? extends EnterpriseServiceDomain> getById(long enterpriseServiceId) {
        boolean takesCustomAppointments = enterpriseServiceRepository
                .findTakesCustomAppointmentsByEnterpriseServiceId(enterpriseServiceId)
                .orElseThrow();

        if (takesCustomAppointments) {
            return customAppointmentsEnterpriseServiceService.getByEnterpriseServiceId(enterpriseServiceId);
        } else {
            return nonCustomAppointmentsEnterpriseServiceService.getByEnterpriseServiceId(enterpriseServiceId);
        }
    }

    public List<EnterpriseServiceEntity> findByEnterpriseId(long enterpriseId) {
        return enterpriseServiceRepository.findByEnterpriseId(enterpriseId);
    }

    public List<? extends TimeSlot> findFreeSlots(long enterpriseServiceId, OffsetDateTime start, OffsetDateTime end) {
        TimeSlotWithOccupancy slot = new TimeSlotWithOccupancy(start, end, 10);
        return List.of(slot);
        // TODO
    }

    public ZoneId getTimeZoneByServiceId(long enterpriseServiceId) {
        // TODO java.util.NoSuchElementException causes error 500
        EnterpriseServiceDomain record = getById(enterpriseServiceId).orElseThrow();
        return record.timezone();
    }

    public boolean isEnterpriseServiceCustom(long enterpriseServiceId) {
        return enterpriseServiceRepository.findTakesCustomAppointmentsByEnterpriseServiceId(enterpriseServiceId)
                .orElseThrow();
    }

}
