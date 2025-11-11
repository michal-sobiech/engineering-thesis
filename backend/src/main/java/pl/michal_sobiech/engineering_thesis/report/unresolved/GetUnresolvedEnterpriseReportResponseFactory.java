package pl.michal_sobiech.engineering_thesis.report.unresolved;

import org.SwaggerCodeGenExample.model.GetUnresolvedEnterpriseReportResponse;
import org.SwaggerCodeGenExample.model.GetUnresolvedEnterpriseReportResponse.ReportSubjectTypeEnum;

import pl.michal_sobiech.engineering_thesis.report.enterprise.UnresolvedEnterpriseReport;

public class GetUnresolvedEnterpriseReportResponseFactory {

    public static GetUnresolvedEnterpriseReportResponse fromUnresolvedEnterpriseReport(
            UnresolvedEnterpriseReport report) {
        return new GetUnresolvedEnterpriseReportResponse(
                report.reportId(),
                ReportSubjectTypeEnum.ENTERPRISE,
                report.enterpriseId(),
                report.enterpriseName(),
                report.creatorUserGroup().toString(),
                report.creatorUserId(),
                report.creatorUsername());
    }

}
