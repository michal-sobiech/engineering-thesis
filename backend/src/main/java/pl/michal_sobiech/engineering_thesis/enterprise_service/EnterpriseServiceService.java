package pl.michal_sobiech.engineering_thesis.enterprise_service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot.EnterpriseServiceSlotEntity;
import pl.michal_sobiech.engineering_thesis.enterprise_service_slot.EnterpriseServiceSlotService;
import pl.michal_sobiech.engineering_thesis.time_slot.TimeSlot;
import pl.michal_sobiech.engineering_thesis.time_slot.TimeSlotWithOccupancy;
import pl.michal_sobiech.engineering_thesis.utils.DateUtils;
import pl.michal_sobiech.engineering_thesis.utils.LocalDateTimeWindow;
import pl.michal_sobiech.engineering_thesis.utils.LocalTimeWindow;

@Component
@RequiredArgsConstructor
public class EnterpriseServiceService {

    private final EnterpriseServiceRepository enterpriseServiceRepository;
    private final EnterpriseServiceSlotService enterpriseServiceSlotService;

    public List<EnterpriseServiceEntity> findByEnterpriseId(long enterpriseId) {
        return enterpriseServiceRepository.findByEnterpriseId(enterpriseId);
    }

    public Optional<EnterpriseServiceEntity> findById(long enterpriseServiceId) {
        return enterpriseServiceRepository.findById(enterpriseServiceId);
    }

    public List<? extends TimeSlot> findFreeSlots(long enterpriseServiceId, OffsetDateTime start, OffsetDateTime end) {
        TimeSlotWithOccupancy slot = new TimeSlotWithOccupancy(start, end, 10);
        return List.of(slot);
        // EnterpriseServiceEntity enterpiseService =
        // findById(enterpriseServiceId).orElseThrow();
        // if (enterpiseService.isTakesCustomAppointments()) {
        // return findFreeSlotsOfServiceWithCustomAppointments(enterpiseService, start,
        // end);
        // } else {
        // return findFreeSlotsOfServiceWithoutCustomAppointments(enterpiseService,
        // start, end);
        // }
    }

    // private List<TimeSlotNoOccupancy>
    // findFreeSlotsOfServiceWithCustomAppointments(
    // EnterpriseServiceEntity enterpriseService,
    // OffsetDateTime start,
    // OffsetDateTime end) {
    // ZoneId enterpriseTimeZone = enterpriseService.getTimeZone();
    // OffsetDateTime startInEnterpriseTz =
    // start.atZoneSameInstant(enterpriseTimeZone).toOffsetDateTime();
    // OffsetDateTime endInEnterpriseTz =
    // end.atZoneSameInstant(enterpriseTimeZone).toOffsetDateTime();

    // List<EnterpriseServiceSlotEntity> weekTemplateSlots =
    // enterpriseServiceSlotService
    // .findAllByEnterpriseServiceId(enterpriseService.getEnterpriseServiceId());
    // Map<DayOfWeek, List<EnterpriseServiceSlotEntity>> weekTemplateSlotMap =
    // weekTemplateSlots.stream()
    // .collect(Collectors.groupingBy(EnterpriseServiceSlotEntity::getDayOfWeek));

    // List<TimeSlotNoOccupancy> slots = new ArrayList<>();
    // for (OffsetDateTime date = startInEnterpriseTz;
    // !date.isAfter(endInEnterpriseTz); date = date.plusDays(1)) {
    // DayOfWeek dayOfWeek = date.getDayOfWeek();
    // slots.
    // }

    // List<TimeSlotNoOccupancy> weekTemplateSlotsWithTimeZone =
    // weekTemplateSlots.stream().map(slot -> {

    // // return new TimeSlotNoOccupancy(
    // // slot.g);
    // // });

    // List<TimeSlotNoOccupancy> slots = new ArrayList<>();

    // }

    // private List<TimeSlotWithOccupancy>
    // findFreeSlotsOfServiceWithoutCustomAppointments(
    // EnterpriseServiceEntity enterpriseService,
    // OffsetDateTime start,
    // OffsetDateTime end) {

    // }

    public ZoneId getTimeZoneByServiceId(long enterpriseServiceId) {
        return enterpriseServiceRepository.findTimeZoneByEnterpriseServiceId(enterpriseServiceId).orElseThrow();
    }

    public List<LocalDateTimeWindow> getAvailabilityTemplateForDateRange(long enterpriseServiceId, LocalDate from,
            LocalDate to) {
        List<LocalDateTimeWindow> out = List.of();
        for (LocalDate date : DateUtils.getAllDatesBetweenIncludingBorders(from, to)) {
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            List<LocalTimeWindow> windowsForDayOfWeek = getAvailabilityTemplateForDayOfWeek(enterpriseServiceId,
                    dayOfWeek);
            List<LocalDateTimeWindow> datetimeWindowsForDayOfWeek = windowsForDayOfWeek.stream()
                    .map(window -> new LocalDateTimeWindow(
                            LocalDateTime.of(date, window.start()),
                            LocalDateTime.of(date, window.end())))
                    .toList();
            out.addAll(datetimeWindowsForDayOfWeek);
        }
        return out;
    }

    public List<LocalTimeWindow> getAvailabilityTemplateForDayOfWeek(long enterpiseServiceId, DayOfWeek dayOfWeek) {
        List<EnterpriseServiceSlotEntity> slots = enterpriseServiceSlotService
                .getAllByEnterpriseServiceIdAndDayOfWeek(enterpiseServiceId, dayOfWeek);
        return slots.stream().map(slot -> new LocalTimeWindow(slot.getStartTime(), slot.getEndTime())).toList();
    }

}
