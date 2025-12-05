package pl.michal_sobiech.core.report.enterprise;

import pl.michal_sobiech.core.report.UnresolvedReport;
import pl.michal_sobiech.core.user.UserGroup;

public record UnresolvedEnterpriseReport(

        long reportId,

        long creatorUserId,
        UserGroup creatorUserGroup,
        String creatorUsername,

        long enterpriseId,
        String enterpriseName

) implements UnresolvedReport {

    public static UnresolvedEnterpriseReport fromEnterpriseReport(EnterpriseReport report) {
        if (report.isResolved()) {
            throw new IllegalArgumentException();
        }

        return new UnresolvedEnterpriseReport(
                report.reportId(),
                report.creatorUserId(),
                report.creatorUserGroup(),
                report.creatorUsername(),
                report.enterpriseId(),
                report.entepriseName());
    }

}
