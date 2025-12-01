package pl.michal_sobiech.engineering_thesis.report.enterprise;

import pl.michal_sobiech.engineering_thesis.report.Report;
import pl.michal_sobiech.engineering_thesis.user.UserGroup;

public record EnterpriseReport(

        long reportId,

        long creatorUserId,
        UserGroup creatorUserGroup,
        String creatorUsername,

        long enterpriseId,
        String entepriseName,

        boolean isResolved

) implements Report {
}
