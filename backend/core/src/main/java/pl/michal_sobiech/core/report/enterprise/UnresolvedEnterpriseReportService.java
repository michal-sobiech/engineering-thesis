package pl.michal_sobiech.core.report.enterprise;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.report.ReportRepository;

@RequiredArgsConstructor
public class UnresolvedEnterpriseReportService {

    private final ReportRepository reportRepository;
    private final EnterpriseReportFactory enterpriseReportFactory;

    public Optional<UnresolvedEnterpriseReport> getByReportId(long reportId) {
        return reportRepository.findDetailedUnresolvedReportByReportId(reportId)
                .map(enterpriseReportFactory::fromRecord)
                .map(UnresolvedEnterpriseReport::fromEnterpriseReport);
    }

}
