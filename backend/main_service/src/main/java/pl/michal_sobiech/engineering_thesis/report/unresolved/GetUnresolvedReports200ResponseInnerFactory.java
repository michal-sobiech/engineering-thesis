package pl.michal_sobiech.engineering_thesis.report.unresolved;

import org.SwaggerCodeGenExample.model.GetUnresolvedReports200ResponseInner;

import pl.michal_sobiech.core.report.UnresolvedReport;
import pl.michal_sobiech.core.report.enterprise.UnresolvedEnterpriseReport;
import pl.michal_sobiech.core.report.enterprise_service.UnresolvedEnterpriseServiceReport;
import pl.michal_sobiech.core.report.review.UnresolvedReviewReport;

public class GetUnresolvedReports200ResponseInnerFactory {

    public static GetUnresolvedReports200ResponseInner fromUnresolvedReport(UnresolvedReport report) {
        return switch (report) {
            case UnresolvedEnterpriseReport enterpriseReport ->
                GetUnresolvedEnterpriseReportResponseFactory.fromUnresolvedEnterpriseReport(enterpriseReport);
            case UnresolvedEnterpriseServiceReport enterpriseServiceReport ->
                GetUnresolvedEnterpriseServiceReportResponseFactory.fromUnresolvedReport(enterpriseServiceReport);
            case UnresolvedReviewReport reviewReport ->
                GetUnresolvedReviewReportResponseFactory.fromUnresolvedReport(reviewReport);
            default -> throw new IllegalStateException("Unsupported report type: " + report.getClass().getName());
        };
    }

}
