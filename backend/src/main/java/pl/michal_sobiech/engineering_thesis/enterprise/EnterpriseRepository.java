package pl.michal_sobiech.engineering_thesis.enterprise;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {

    public List<Enterprise> findAllByEntrepreneurId(long entrepreneurId);
}
