package pl.michal_sobiech.engineering_thesis.report.enterprise_service;

import pl.michal_sobiech.engineering_thesis.report.UnresolvedReport;
import pl.michal_sobiech.shared.user.UserGroup;

public record UnresolvedEnterpriseServiceReport(

        long reportId,

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
                report.creatorUserId(),
                report.creatorUserGroup(),
                report.creatorUsername(),
                report.enterpriseServiceId(),
                report.enterpriseServiceName());
    }

}
