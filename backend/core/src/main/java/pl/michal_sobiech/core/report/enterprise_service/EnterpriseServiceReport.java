package pl.michal_sobiech.core.report.enterprise_service;

import pl.michal_sobiech.core.report.Report;
import pl.michal_sobiech.core.user.UserGroup;

public record EnterpriseServiceReport(
        long reportId,

        String content,

        long creatorUserId,
        UserGroup creatorUserGroup,
        String creatorUsername,

        long enterpriseServiceId,
        String enterpriseServiceName,

        boolean isResolved

) implements Report {

}
