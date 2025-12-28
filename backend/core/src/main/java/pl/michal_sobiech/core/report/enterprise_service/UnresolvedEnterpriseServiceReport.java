package pl.michal_sobiech.core.report.enterprise_service;

import pl.michal_sobiech.core.report.UnresolvedReport;
import pl.michal_sobiech.core.user.UserGroup;

public record UnresolvedEnterpriseServiceReport(

        long reportId,

        String content,

        long creatorUserId,
        UserGroup creatorUserGroup,
        String creatorUsername,

        long enterpriseServiceId,
        String enterpriseServiceName

) implements UnresolvedReport {

    public static UnresolvedEnterpriseServiceReport fromEnterpriseServiceReport(EnterpriseServiceReport report) {
        if (report.isResolved()) {
            throw new IllegalArgumentException();
        }

        return new UnresolvedEnterpriseServiceReport(
                report.reportId(),
                report.content(),
                report.creatorUserId(),
                report.creatorUserGroup(),
                report.creatorUsername(),
                report.enterpriseServiceId(),
                report.enterpriseServiceName());
    }

}
