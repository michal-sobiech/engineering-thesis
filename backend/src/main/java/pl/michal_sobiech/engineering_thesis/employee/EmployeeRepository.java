package pl.michal_sobiech.engineering_thesis.employee;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    public boolean existsByEnterpriseIdAndUsername(long enterpriseId, String username);

    public Optional<Employee> findByEnterpriseIdAndUsername(long enterpriseId, String username);
}
