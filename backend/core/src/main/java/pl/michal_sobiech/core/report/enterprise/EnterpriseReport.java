package pl.michal_sobiech.core.report.enterprise;

import pl.michal_sobiech.core.report.Report;
import pl.michal_sobiech.core.user.UserGroup;

public record EnterpriseReport(

        long reportId,

        String content,

        long creatorUserId,
        UserGroup creatorUserGroup,
        String creatorUsername,

        long enterpriseId,
        String entepriseName,

        boolean isResolved

) implements Report {
}
