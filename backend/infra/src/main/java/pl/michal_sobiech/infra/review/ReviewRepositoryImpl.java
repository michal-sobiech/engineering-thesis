package pl.michal_sobiech.infra.review;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.review.ReviewEntity;
import pl.michal_sobiech.core.review.ReviewRepository;

@Component
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository {

    private final SpringReviewRepository springReviewRepository;

    @Override
    public ReviewEntity save(ReviewEntity record) {
        return springReviewRepository.save(record);
    }

    @Override
    public Optional<ReviewEntity> findById(long reviewId) {
        return springReviewRepository.findById(reviewId);
    }

    @Override
    public List<ReviewEntity> findAllBySubjectEnterpriseServiceId(long subjectEnterpriseServiceId) {
        return springReviewRepository.findAllBySubjectEnterpriseServiceId(subjectEnterpriseServiceId);
    }

}
