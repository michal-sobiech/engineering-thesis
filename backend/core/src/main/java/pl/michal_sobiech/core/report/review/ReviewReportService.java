package pl.michal_sobiech.core.report.review;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.report.ReportRepository;

@RequiredArgsConstructor
public class ReviewReportService {

    private final ReportRepository reportRepository;
    private final ReviewReportFactory reviewReportFactory;

    public Optional<ReviewReport> getByReportId(long reportId) {
        return reportRepository.findDetailedUnresolvedReportByReportId(reportId)
                .map(reviewReportFactory::fromRecord);
    }

}
