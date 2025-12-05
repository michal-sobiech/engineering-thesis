package pl.michal_sobiech.engineering_thesis.review;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pl.michal_sobiech.core.review.ReviewRepository;
import pl.michal_sobiech.core.review.ReviewService;

@Configuration
public class ReviewConfig {

    @Bean
    public ReviewService reviewService(ReviewRepository reviewRepository) {
        return new ReviewService(reviewRepository);
    }

}
