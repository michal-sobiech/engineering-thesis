package pl.michal_sobiech.core.report.enterprise;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.report.ReportRepository;

@RequiredArgsConstructor
public class EnterpriseReportService {

    private final ReportRepository reportRepository;
    private final EnterpriseReportFactory enterpriseReportFactory;

    public Optional<EnterpriseReport> getByReportId(long reportId) {
        return reportRepository.findDetailedUnresolvedReportByReportId(reportId)
                .map(enterpriseReportFactory::fromRecord);
    }

}
