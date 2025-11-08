package pl.michal_sobiech.engineering_thesis.review;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
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

}
