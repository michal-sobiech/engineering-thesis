package pl.michal_sobiech.infra.review;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pl.michal_sobiech.core.review.ReviewEntity;

public interface SpringReviewRepository extends JpaRepository<ReviewEntity, Long> {

    @Query("""
            SELECT r
            FROM ReviewEntity r
            WHERE r.subjectEnterpriseServiceId = subjectEnterpriseServiceId
            """)
    public List<ReviewEntity> findAllBySubjectEnterpriseServiceId(
            @Value("subjectEnterpriseServiceId") long subjectEnterpriseServiceId);

}