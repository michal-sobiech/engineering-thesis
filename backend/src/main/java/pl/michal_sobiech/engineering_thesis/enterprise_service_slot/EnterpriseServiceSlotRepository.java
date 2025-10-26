package pl.michal_sobiech.engineering_thesis.enterprise_service_slot;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pl.michal_sobiech.engineering_thesis.enterprise_service.EnterpriseServiceCathegory;

public interface EnterpriseServiceSlotRepository extends JpaRepository<EnterpriseServiceSlotEntity, Long> {

    public List<EnterpriseServiceSlotEntity> findAllByEnterpriseServiceId(long enterpriseServiceId);

    @Query(value = """
            SELECT * FROM enterprise_service_slot slot
            JOIN enterprise_service service ON service.enterprise_service_id = slot.enterprise_service_id
            JOIN enterprise enterprise ON enteprise.enterprise_id = service.enterprise_id
            WHERE (:serviceName IS NULL OR LOWER(service.name) LIKE LOWER('%' || :serviceName || '%'))
            AND (:enterpriseName IS NULL OR LOWER(enterprise.name) LIKE LOWER('%' || :enterpriseName || '%'))
            AND (:startDate IS NULL OR slot.start_time >= :startDate)
            AND (:endDate IS NULL OR slot.end_time <= :endDate)
            AND (:cathegory IS NULL OR service.cathegory = :cathegory)
            AND (ST_DistanceSphere(
                ST_MakePoint(service.longitude, service.latitude),
                ST_MakePoint(:customerLongitude, :customerLatitude)
            )) <= :maxDistance
            """, nativeQuery = true)
    public List<EnterpriseServiceSlotEntity> filterNoCustomAppointmentsServiceSlots(
            @Param("serviceName") String serviceName,
            @Param("enterpriseName") String enterpriseName,
            @Param("startDate") OffsetDateTime startDate,
            @Param("endDate") OffsetDateTime endDate,
            @Param("cathegory") EnterpriseServiceCathegory cathegory,
            @Param("customerLatitude") double customerLatitude,
            @Param("customerLongitude") double customerLongitude,
            @Param("maxDistance") double maxDistanceFromCustomerToService);

}
