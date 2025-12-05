package pl.michal_sobiech.core.report.unresolved;

import pl.michal_sobiech.core.report.Report;
import pl.michal_sobiech.core.report.UnresolvedReport;
import pl.michal_sobiech.core.report.enterprise.EnterpriseReport;
import pl.michal_sobiech.core.report.enterprise.UnresolvedEnterpriseReport;
import pl.michal_sobiech.core.report.enterprise_service.EnterpriseServiceReport;
import pl.michal_sobiech.core.report.enterprise_service.UnresolvedEnterpriseServiceReport;
import pl.michal_sobiech.core.report.review.ReviewReport;
import pl.michal_sobiech.core.report.review.UnresolvedReviewReport;

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
