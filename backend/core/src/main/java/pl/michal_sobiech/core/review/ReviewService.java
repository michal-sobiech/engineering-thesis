package pl.michal_sobiech.engineering_thesis.review;

import java.util.Optional;

import org.springframework.stereotype.Service;

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

}
