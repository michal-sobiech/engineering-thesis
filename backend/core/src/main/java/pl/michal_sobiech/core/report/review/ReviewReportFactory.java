package pl.michal_sobiech.core.report.review;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.report.GetUnresolvedReportsRow;
import pl.michal_sobiech.core.review.Review;
import pl.michal_sobiech.core.review.ReviewService;

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
