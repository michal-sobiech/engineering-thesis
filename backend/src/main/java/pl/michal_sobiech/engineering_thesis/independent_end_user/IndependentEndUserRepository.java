package pl.michal_sobiech.engineering_thesis.independent_end_user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndependentEndUserRepository extends JpaRepository<IndependentEndUser, Long> {
    Optional<IndependentEndUser> findById(Long id);
}
