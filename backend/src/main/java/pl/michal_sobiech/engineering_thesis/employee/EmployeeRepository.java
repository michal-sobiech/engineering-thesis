package pl.michal_sobiech.engineering_thesis.employee;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    public boolean existsByEnterpriseIdAndUsername(long enterpriseId, String username);
}
