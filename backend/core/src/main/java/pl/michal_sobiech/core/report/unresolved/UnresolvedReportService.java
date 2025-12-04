package pl.michal_sobiech.engineering_thesis.report.unresolved;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.report.ReportFactory;
import pl.michal_sobiech.engineering_thesis.report.ReportRepository;
import pl.michal_sobiech.engineering_thesis.report.UnresolvedReport;


@RequiredArgsConstructor
public class UnresolvedReportService {

    private final ReportRepository reportRepository;
    private final ReportFactory reportFactory;

    public List<UnresolvedReport> getUnresolvedReports() {
        return reportRepository.findDetailedUnresolvedReports()
                .stream()
                .map(reportFactory::fromRecord)
                .map(UnresolvedReportFactory::fromReport)
                .collect(Collectors.toList());
    }

    public Optional<UnresolvedReport> getUnresolvedReportByReportId(long reportId) {
        return reportRepository.findDetailedUnresolvedReportByReportId(reportId)
                .map(reportFactory::fromRecord)
                .map(UnresolvedReportFactory::fromReport);
    }

    public void resolveReport(long reportId) {
        int numChangedRows = reportRepository.resolveReport(reportId);
        if (numChangedRows == 0) {
            String message = "Couldnt't find unresolved report with id %d".formatted(reportId);
            throw new EntityNotFoundException(message);
        } else if (numChangedRows > 1) {
            throw new IllegalStateException("Expected exactly one row, not more");
        }
    }
}
