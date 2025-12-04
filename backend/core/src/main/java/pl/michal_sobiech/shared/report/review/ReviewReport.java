package pl.michal_sobiech.engineering_thesis.report.review;

import pl.michal_sobiech.engineering_thesis.report.Report;
import pl.michal_sobiech.shared.user.UserGroup;

public record ReviewReport(

                long reportId,

                long creatorUserId,
                UserGroup creatorUserGroup,
                String creatorUsername,

                long reviewId,
                String reviewText,

                boolean isResolved

) implements Report {

}
