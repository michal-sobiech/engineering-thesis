package pl.michal_sobiech.core.report.unresolved;

import org.SwaggerCodeGenExample.model.GetUnresolvedReviewReportResponse;
import org.SwaggerCodeGenExample.model.GetUnresolvedReviewReportResponse.ReportSubjectTypeEnum;

import pl.michal_sobiech.engineering_thesis.report.review.UnresolvedReviewReport;

public class GetUnresolvedReviewReportResponseFactory {

    public static GetUnresolvedReviewReportResponse fromUnresolvedReport(UnresolvedReviewReport report) {
        return new GetUnresolvedReviewReportResponse(
                report.reportId(),
                ReportSubjectTypeEnum.REVIEW,
                report.reviewId(),
                report.reviewText(),
                report.creatorUserGroup().toString(),
                report.creatorUserId(),
                report.creatorUsername());
    }

}
