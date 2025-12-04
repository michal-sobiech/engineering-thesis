package pl.michal_sobiech.engineering_thesis.report.enterprise;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.report.ReportRepository;

@Service
@RequiredArgsConstructor
public class EnterpriseReportService {

    private final ReportRepository reportRepository;
    private final EnterpriseReportFactory enterpriseReportFactory;

    public Optional<EnterpriseReport> getByReportId(long reportId) {
        return reportRepository.findDetailedUnresolvedReportByReportId(reportId)
                .map(enterpriseReportFactory::fromRecord);
    }

}
