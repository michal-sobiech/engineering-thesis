package pl.michal_sobiech.core.report.enterprise_service;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.report.ReportRepository;

@RequiredArgsConstructor
public class UnresolvedEnterpriseServiceReportService {

    private final ReportRepository reportRepository;
    private final EnterpriseServiceReportFactory enterpriseServiceReportFactory;

    public Optional<UnresolvedEnterpriseServiceReport> getByReportId(long reportId) {
        return reportRepository.findDetailedUnresolvedReportByReportId(reportId)
                .map(enterpriseServiceReportFactory::fromRecord)
                .map(UnresolvedEnterpriseServiceReport::fromEnterpriseServiceReport);
    }

}
