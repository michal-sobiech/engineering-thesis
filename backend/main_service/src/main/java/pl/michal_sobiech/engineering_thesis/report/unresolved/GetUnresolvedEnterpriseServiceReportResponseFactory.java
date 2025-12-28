package pl.michal_sobiech.engineering_thesis.report.unresolved;

import org.SwaggerCodeGenExample.model.GetUnresolvedEnterpriseServiceReportResponse;

import pl.michal_sobiech.core.report.enterprise_service.UnresolvedEnterpriseServiceReport;

public class GetUnresolvedEnterpriseServiceReportResponseFactory {

    public static GetUnresolvedEnterpriseServiceReportResponse fromUnresolvedReport(
            UnresolvedEnterpriseServiceReport report) {
        return new GetUnresolvedEnterpriseServiceReportResponse(
                report.reportId(),
                report.enterpriseServiceId(),
                report.content(),
                report.enterpriseServiceName(),
                report.creatorUserGroup().toString(),
                report.creatorUserId(),
                report.creatorUsername());
    }

}
