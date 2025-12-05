package pl.michal_sobiech.core.report.enterprise_service;

import pl.michal_sobiech.engineering_thesis.report.Report;
import pl.michal_sobiech.shared.user.UserGroup;

public record EnterpriseServiceReport(
        long reportId,

        long creatorUserId,
        UserGroup creatorUserGroup,
        String creatorUsername,

        long enterpriseServiceId,
        String enterpriseServiceName,

        boolean isResolved

) implements Report {

}
