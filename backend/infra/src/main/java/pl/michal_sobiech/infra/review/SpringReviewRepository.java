package pl.michal_sobiech.infra.review;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.michal_sobiech.core.review.ReviewEntity;

public interface SpringReviewRepository extends JpaRepository<ReviewEntity, Long> {

}