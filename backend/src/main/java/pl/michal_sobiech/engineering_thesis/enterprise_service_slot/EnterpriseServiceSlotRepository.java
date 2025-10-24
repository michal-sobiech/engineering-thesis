package pl.michal_sobiech.engineering_thesis.enterprise_service_slot;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EnterpriseServiceSlotRepository extends JpaRepository<EnterpriseServiceSlotEntity, Long> {

    public List<EnterpriseServiceSlotEntity> findAllByEnterpriseServiceId(long enterpriseServiceId);

}
