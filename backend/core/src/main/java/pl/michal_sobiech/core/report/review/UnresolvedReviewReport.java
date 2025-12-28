package pl.michal_sobiech.core.report.review;

import pl.michal_sobiech.core.report.UnresolvedReport;
import pl.michal_sobiech.core.user.UserGroup;

public record UnresolvedReviewReport(

        long reportId,

        String content,

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
                report.content(),
                report.creatorUserId(),
                report.creatorUserGroup(),
                report.creatorUsername(),
                report.reviewId(),
                report.reviewText());
    }

}
