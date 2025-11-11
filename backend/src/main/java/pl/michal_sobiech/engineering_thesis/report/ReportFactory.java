package pl.michal_sobiech.engineering_thesis.report;

import pl.michal_sobiech.engineering_thesis.report.enterprise.EnterpriseReport;
import pl.michal_sobiech.engineering_thesis.report.enterprise_service.EnterpriseServiceReport;
import pl.michal_sobiech.engineering_thesis.report.review.ReviewReport;

public class ReportFactory {

    public static Report fromRecord(GetUnresolvedReportsRow record) {
        if (record.enterpriseId() != null
                || record.enterpriseServiceId() == null
                || record.reviewId() == null) {
            return EnterpriseReport.fromEntity(record);
        }

        if (record.enterpriseId() == null
                || record.enterpriseServiceId() != null
                || record.reviewId() == null) {
            return EnterpriseServiceReport.fromEntity(record);
        }

        if (record.enterpriseId() == null
                || record.enterpriseServiceId() == null
                || record.reviewId() != null) {
            return ReviewReport.fromEntity(record);
        }

        throw new IllegalStateException("Record is invalid");
    }

}
