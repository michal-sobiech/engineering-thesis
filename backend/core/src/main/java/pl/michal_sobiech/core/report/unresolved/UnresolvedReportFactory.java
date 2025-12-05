package pl.michal_sobiech.core.report.unresolved;

import pl.michal_sobiech.engineering_thesis.report.Report;
import pl.michal_sobiech.engineering_thesis.report.UnresolvedReport;
import pl.michal_sobiech.engineering_thesis.report.enterprise.EnterpriseReport;
import pl.michal_sobiech.engineering_thesis.report.enterprise.UnresolvedEnterpriseReport;
import pl.michal_sobiech.engineering_thesis.report.enterprise_service.EnterpriseServiceReport;
import pl.michal_sobiech.engineering_thesis.report.enterprise_service.UnresolvedEnterpriseServiceReport;
import pl.michal_sobiech.engineering_thesis.report.review.ReviewReport;
import pl.michal_sobiech.engineering_thesis.report.review.UnresolvedReviewReport;

public class UnresolvedReportFactory {

    public static UnresolvedReport fromReport(Report report) {
        return switch (report) {
            case EnterpriseReport enterpriseReport ->
                UnresolvedEnterpriseReport.fromEnterpriseReport(enterpriseReport);
            case EnterpriseServiceReport enterpriseServiceReport ->
                UnresolvedEnterpriseServiceReport.fromEnterpriseServiceReport(enterpriseServiceReport);
            case ReviewReport reviewReport -> UnresolvedReviewReport.fromReviewReport(reviewReport);
            default -> throw new IllegalArgumentException();
        };
    }

}
