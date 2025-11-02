package pl.michal_sobiech.engineering_thesis.enterprise_service_slot;

import java.time.DayOfWeek;
import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceCathegory;

public interface EnterpriseServiceSlotRepository extends JpaRepository<EnterpriseServiceSlotEntity, Long> {

    public List<EnterpriseServiceSlotEntity> findAllByEnterpriseServiceId(long enterpriseServiceId);

    public List<EnterpriseServiceSlotEntity> findAllByEnterpriseServiceIdAndDayOfWeek(long enterpriseServiceId,
            DayOfWeek dayOfWeek);

    @Query(value = """
            SELECT
            service.enterprise_service_id AS enterpriseServiceId,
            service.name AS serviceName,
            enterprise.name AS enterpriseName,
            slot.start_time AS startTime,
            slot.end_time AS endTime,
            service.price AS price
            FROM enterprise_service_slot slot
            JOIN enterprise_service service ON service.enterprise_service_id = slot.enterprise_service_id
            JOIN enterprise enterprise ON enterprise.enterprise_id = service.enterprise_id
            WHERE (:serviceName IS NULL OR LOWER(service.name) LIKE LOWER('%' || :serviceName || '%'))
            AND (:enterpriseName IS NULL OR LOWER(enterprise.name) LIKE LOWER('%' || :enterpriseName || '%'))
            AND (CAST(:startDate AS timestamptz) IS NULL OR slot.start_time >= :startDate)
            AND (CAST(:endDate AS timestamptz) IS NULL OR slot.end_time <= :endDate)
            AND (:cathegory IS NULL OR CAST(service.cathegory AS text) = :#{#cathegory?.name()})
            AND (ST_DistanceSphere(
                ST_MakePoint(service.longitude, service.latitude),
                ST_MakePoint(:customerLongitude, :customerLatitude)
            ) / 1000 <= :maxDistanceChosenByCustomerKm)
            """, nativeQuery = true)
    public List<ServiceSearchSlot> filterNoCustomAppointmentsServiceSlots(
            @Param("serviceName") String serviceName,
            @Param("enterpriseName") String enterpriseName,
            @Param("startDate") OffsetDateTime startDate,
            @Param("endDate") OffsetDateTime endDate,
            @Param("cathegory") EnterpriseServiceCathegory cathegory,
            @Param("customerLatitude") double customerLatitude,
            @Param("customerLongitude") double customerLongitude,
            @Param("maxDistanceChosenByCustomerKm") double maxDistanceChosenByCustomerKm);

    @Query(value = """
            SELECT
            service.enterprise_service_id AS enterpriseServiceId,
            service.name AS serviceName,
            enterprise.name AS enterpriseName,
            slot.start_time AS startTime,
            slot.end_time AS endTime,
            service.price AS price
            FROM enterprise_service_slot slot
            JOIN enterprise_service service ON service.enterprise_service_id = slot.enterprise_service_id
            JOIN enterprise enterprise ON enterprise.enterprise_id = service.enterprise_id
            WHERE (:serviceName IS NULL OR LOWER(service.name) LIKE LOWER('%' || :serviceName || '%'))
            AND (:enterpriseName IS NULL OR LOWER(enterprise.name) LIKE LOWER('%' || :enterpriseName || '%'))
            AND (CAST(:startDate AS timestamptz) IS NULL OR slot.start_time >= :startDate)
            AND (CAST(:endDate AS timestamptz) IS NULL OR slot.end_time <= :endDate)
            AND (:cathegory IS NULL OR CAST(service.cathegory AS text) = :#{#cathegory?.name()})
            AND (ST_DistanceSphere(
                ST_MakePoint(service.longitude, service.latitude),
                ST_MakePoint(:customerLongitude, :customerLatitude)
            ) / 1000 <= :maxDistanceChosenByCustomerKm)
            AND (ST_DistanceSphere(
                ST_MakePoint(service.longitude, service.latitude),
                ST_MakePoint(:customerLongitude, :customerLatitude)
            ) / 1000 <= service.max_distance_km)
            """, nativeQuery = true)
    public List<ServiceSearchSlot> filterCustomAppointmentsServiceSlots(
            @Param("serviceName") String serviceName,
            @Param("enterpriseName") String enterpriseName,
            @Param("startDate") OffsetDateTime startDate,
            @Param("endDate") OffsetDateTime endDate,
            @Param("cathegory") EnterpriseServiceCathegory cathegory,
            @Param("customerLatitude") double customerLatitude,
            @Param("customerLongitude") double customerLongitude,
            @Param("maxDistanceChosenByCustomerKm") double maxDistanceChosenByCustomerKm);

}
