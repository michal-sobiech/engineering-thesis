package pl.michal_sobiech.engineering_thesis.report.unresolved;

import org.SwaggerCodeGenExample.model.GetUnresolvedEnterpriseServiceReportResponse;
import org.SwaggerCodeGenExample.model.GetUnresolvedEnterpriseServiceReportResponse.ReportSubjectTypeEnum;

import pl.michal_sobiech.core.report.enterprise_service.UnresolvedEnterpriseServiceReport;

public class GetUnresolvedEnterpriseServiceReportResponseFactory {

    public static GetUnresolvedEnterpriseServiceReportResponse fromUnresolvedReport(
            UnresolvedEnterpriseServiceReport report) {
        return new GetUnresolvedEnterpriseServiceReportResponse(
                report.reportId(),
                ReportSubjectTypeEnum.SERVICE,
                report.enterpriseServiceId(),
                report.enterpriseServiceName(),
                report.creatorUserGroup().toString(),
                report.creatorUserId(),
                report.creatorUsername());
    }

}
