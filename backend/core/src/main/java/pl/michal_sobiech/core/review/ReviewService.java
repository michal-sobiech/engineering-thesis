package pl.michal_sobiech.core.review;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public void createReview(
            long creatorCustomerUserId,
            long enterpriseServiceId,
            short starsOutOf5,
            String text) {
        ReviewEntity review = new ReviewEntity(
                null,
                creatorCustomerUserId,
                enterpriseServiceId,
                starsOutOf5,
                text,
                false);
        review = reviewRepository.save(review);
    }

    public Optional<Review> getById(long reviewId) {
        return reviewRepository.findById(reviewId).map(Review::fromEntity);
    }

    public List<Review> getAllBySubjectEnterpriseServiceId(long enterpriseServiceId) {
        return reviewRepository.findAllBySubjectEnterpriseServiceId(enterpriseServiceId)
                .stream()
                .map(Review::fromEntity)
                .collect(Collectors.toList());
    }

}
