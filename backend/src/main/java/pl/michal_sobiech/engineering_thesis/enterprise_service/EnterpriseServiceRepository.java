package pl.michal_sobiech.engineering_thesis.enterprise_service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EnterpriseServiceRepository extends JpaRepository<EnterpriseServiceEntity, Long> {

    public List<EnterpriseServiceEntity> findByEnterpriseId(long enterpriseId);

    @Query(value = """
            SELECT
                service.enterprise_service_id AS enterpriseServiceId,
                service.name AS serviceName,
                enterprise.name AS enterpriseName,
                enterprise.address AS address,
                service.price AS price,
                service.time_zone as timezone
            FROM enterprise_service service
            JOIN enterprise enterprise ON enterprise.enterprise_id = service.enterprise_id
            WHERE (service.takes_custom_appointments = :takesCustomAppointments)
            AND (:serviceName IS NULL OR LOWER(service.name) LIKE LOWER('%' || :serviceName || '%'))
            AND (:enterpriseName IS NULL OR LOWER(enterprise.name) LIKE LOWER('%' || :enterpriseName || '%'))
            AND (:cathegory IS NULL OR CAST(service.cathegory AS text) = :#{#cathegory?.name()})
            AND (ST_DistanceSphere(
                ST_MakePoint(service.longitude, service.latitude),
                ST_MakePoint(:customerLongitude, :customerLatitude)
            ) / 1000 <= :maxDistance)
            """, nativeQuery = true)
    public List<EnterpriseServiceSearchResultRow> search(
            @Param("takesCustomAppointments") boolean takesCustomAppointments,
            @Param("serviceName") String serviceName,
            @Param("enterpriseName") String enterpriseName,
            @Param("cathegory") EnterpriseServiceCathegory cathegory,
            @Param("customerLatitude") double customerLatitude,
            @Param("customerLongitude") double customerLongitude,
            @Param("maxDistance") double maxDistance);

}
