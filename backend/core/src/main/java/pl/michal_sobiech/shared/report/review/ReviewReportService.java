package pl.michal_sobiech.engineering_thesis.report.review;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.report.ReportRepository;

@Service
@RequiredArgsConstructor
public class ReviewReportService {

    private final ReportRepository reportRepository;
    private final ReviewReportFactory reviewReportFactory;

    public Optional<ReviewReport> getByReportId(long reportId) {
        return reportRepository.findDetailedUnresolvedReportByReportId(reportId)
                .map(reviewReportFactory::fromRecord);
    }

}
