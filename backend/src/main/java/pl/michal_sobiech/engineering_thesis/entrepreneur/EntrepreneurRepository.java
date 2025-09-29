package pl.michal_sobiech.engineering_thesis.entrepreneur;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntrepreneurRepository extends JpaRepository<Entrepreneur, Long> {

    public boolean existsByIndependentEndUserId(long independentEndUserId);

    public Optional<Long> findEntrepreneurIdByIndependentEndUserId(long independentEndUserId);

}
