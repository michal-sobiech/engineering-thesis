package pl.michal_sobiech.engineering_thesis.report.review;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.report.GetUnresolvedReportsRow;
import pl.michal_sobiech.engineering_thesis.review.Review;
import pl.michal_sobiech.engineering_thesis.review.ReviewService;

@Component
@RequiredArgsConstructor
public class ReviewReportFactory {

    private final ReviewService reviewService;

    public ReviewReport fromRecord(GetUnresolvedReportsRow record) {
        if (record.getEnterpriseId() != null
                || record.getEnterpriseServiceId() != null
                || record.getReviewId() == null) {
            throw new IllegalArgumentException();
        }

        Review review = reviewService.getById(record.getReviewId()).orElseThrow();

        return new ReviewReport(
                record.getReportId(),
                record.getCreatorUserId(),
                record.getCreatorUserGroup(),
                record.getCreatorUsername(),
                record.getReviewId(),
                review.content(),
                record.getIsResolved());
    }

}
