package pl.michal_sobiech.engineering_thesis.report.unresolved;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.report.ReportFactory;
import pl.michal_sobiech.engineering_thesis.report.ReportRepository;
import pl.michal_sobiech.engineering_thesis.report.UnresolvedReport;

@Service
@RequiredArgsConstructor
public class UnresolvedReportService {

    private final ReportRepository reportRepository;

    public List<UnresolvedReport> getUnresolvedReports() {
        return reportRepository.findUnresolvedReports()
                .stream()
                .map(ReportFactory::fromEntity)
                .map(UnresolvedReportFactory::fromReport)
                .collect(Collectors.toList());
    }

}
