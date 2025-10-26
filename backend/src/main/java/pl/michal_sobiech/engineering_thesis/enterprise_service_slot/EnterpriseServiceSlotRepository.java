package pl.michal_sobiech.engineering_thesis.enterprise_service_slot;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EnterpriseServiceSlotRepository extends JpaRepository<EnterpriseServiceSlotEntity, Long> {

    public List<EnterpriseServiceSlotEntity> findAllByEnterpriseServiceId(long enterpriseServiceId);

    @Query("""
        SELECT slot FROM EnterpriseServiceSlotEntity slot
        JOIN EnterpriseServiceEntity service ON service.enterpriseServiceId = slot.enterpriseServiceId
        JOIN EnterpriseEntity enterprise ON enterprise.enterpriseId = service.enterpriseId
        WHERE (:serviceName IS NULL OR LOWER(service.name) LIKE LOWER(CONCAT('%', :serviceName, '%')))
        AND (:enterpriseName IS NULL OR LOWER(enterprise.name) LIKE LOWER(CONCAT('%', :enterpriseName, '%')))
        AND (:startDate IS NULL OR slot.startTime >= :startDate)
        AND (:endDate IS NULL OR slot.endTime <= :endDate)
        AND (:cathegory IS NULL OR service.cathegory = :cathegory)
    """, nativeQuery = true)
    public List<EnterpriseServiceSlotEntity> filterSlots(

    )

}
