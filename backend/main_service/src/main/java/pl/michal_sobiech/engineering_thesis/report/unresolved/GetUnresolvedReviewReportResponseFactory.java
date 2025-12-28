package pl.michal_sobiech.engineering_thesis.report.unresolved;

import org.SwaggerCodeGenExample.model.GetUnresolvedReviewReportResponse;

import pl.michal_sobiech.core.report.review.UnresolvedReviewReport;

public class GetUnresolvedReviewReportResponseFactory {

    public static GetUnresolvedReviewReportResponse fromUnresolvedReport(UnresolvedReviewReport report) {
        return new GetUnresolvedReviewReportResponse(
                report.reportId(),
                report.reviewId(),
                report.content(),
                report.reviewText(),
                report.creatorUserGroup().toString(),
                report.creatorUserId(),
                report.creatorUsername());
    }

}
