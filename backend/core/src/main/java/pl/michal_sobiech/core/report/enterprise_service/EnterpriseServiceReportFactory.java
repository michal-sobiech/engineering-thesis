package pl.michal_sobiech.core.report.enterprise_service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.enterprise_service.EnterpriseServiceDomain;
import pl.michal_sobiech.core.enterprise_service.EnterpriseServiceService;
import pl.michal_sobiech.core.report.GetUnresolvedReportsRow;

@RequiredArgsConstructor
public class EnterpriseServiceReportFactory {

    private final EnterpriseServiceService enterpriseServiceService;

    public EnterpriseServiceReport fromRecord(GetUnresolvedReportsRow record) {
        if (record.getEnterpriseId() != null
                || record.getEnterpriseServiceId() == null
                || record.getReviewId() != null) {
            throw new IllegalArgumentException();
        }

        EnterpriseServiceDomain enterpriseServiceDomain = enterpriseServiceService
                .getById(record.getEnterpriseServiceId()).orElseThrow();

        return new EnterpriseServiceReport(
                record.getReportId(),
                record.getContent(),
                record.getCreatorUserId(),
                record.getCreatorUserGroup(),
                record.getCreatorUsername(),
                record.getEnterpriseServiceId(),
                enterpriseServiceDomain.name(),
                record.getIsResolved());
    }

}
