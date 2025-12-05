package pl.michal_sobiech.core.report.review;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.report.ReportRepository;

@RequiredArgsConstructor
public class UnresolvedReviewReportService {

    private final ReportRepository reportRepository;
    private final ReviewReportFactory enterpriseServiceReportFactory;

    public Optional<UnresolvedReviewReport> getByReportId(long reportId) {
        return reportRepository.findDetailedUnresolvedReportByReportId(reportId)
                .map(enterpriseServiceReportFactory::fromRecord)
                .map(UnresolvedReviewReport::fromReviewReport);
    }

}
