package pl.michal_sobiech.engineering_thesis.customer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    public Optional<Customer> findById(long id);

    public boolean existsByIndependentEndUserId(long independentEndUserId);

    public Optional<Long> findCustomerIdByIndependentEndUserId(long independentEndUserId);
}
