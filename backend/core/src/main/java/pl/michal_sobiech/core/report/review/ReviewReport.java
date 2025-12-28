package pl.michal_sobiech.core.report.review;

import pl.michal_sobiech.core.report.Report;
import pl.michal_sobiech.core.user.UserGroup;

public record ReviewReport(

        long reportId,

        String content,

        long creatorUserId,
        UserGroup creatorUserGroup,
        String creatorUsername,

        long reviewId,
        String reviewText,

        boolean isResolved

) implements Report {

}
