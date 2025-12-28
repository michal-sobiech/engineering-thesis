package pl.michal_sobiech.core.report;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.report.enterprise.EnterpriseReportFactory;
import pl.michal_sobiech.core.report.enterprise_service.EnterpriseServiceReportFactory;
import pl.michal_sobiech.core.report.review.ReviewReportFactory;

@RequiredArgsConstructor
public class ReportFactory {

    private final EnterpriseReportFactory enterpriseReportFactory;
    private final EnterpriseServiceReportFactory enterpriseServiceReportFactory;
    private final ReviewReportFactory reviewReportFactory;

    public Report fromRecord(GetUnresolvedReportsRow record) {
        if (record.getEnterpriseId() != null
                && record.getEnterpriseServiceId() == null
                && record.getReviewId() == null) {
            return enterpriseReportFactory.fromRecord(record);
        }

        if (record.getEnterpriseId() == null
                && record.getEnterpriseServiceId() != null
                && record.getReviewId() == null) {
            return enterpriseServiceReportFactory.fromRecord(record);
        }

        if (record.getEnterpriseId() == null
                && record.getEnterpriseServiceId() == null
                && record.getReviewId() != null) {
            return reviewReportFactory.fromRecord(record);
        }

        throw new IllegalStateException("Record is invalid");
    }

}
