package pl.michal_sobiech.engineering_thesis.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.michal_sobiech.engineering_thesis.model.IndependentEndUser;

@Repository
public interface IndependentEndUserRepository extends JpaRepository<IndependentEndUser, Long> {
    Optional<IndependentEndUser> findById(Long id);
}
