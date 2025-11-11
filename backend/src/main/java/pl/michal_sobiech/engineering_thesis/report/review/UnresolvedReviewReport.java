package pl.michal_sobiech.engineering_thesis.report.review;

import pl.michal_sobiech.engineering_thesis.report.UnresolvedReport;
import pl.michal_sobiech.engineering_thesis.user.UserGroup;

public record UnresolvedReviewReport(

        long reportId,

        long creatorUserId,
        UserGroup creatorUserGroup,
        String creatorUsername,

        long reviewId,
        String reviewText

) implements UnresolvedReport {

    public static UnresolvedReviewReport fromReviewReport(ReviewReport report) {
        if (report.isResolved()) {
            throw new IllegalArgumentException();
        }

        return new UnresolvedReviewReport(
                report.reportId(),
                report.creatorUserId(),
                report.creatorUserGroup(),
                report.creatorUsername(),
                report.reviewId(),
                report.reviewText());
    }

}
