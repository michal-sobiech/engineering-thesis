package pl.michal_sobiech.engineering_thesis.report.enterprise_service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.report.ReportRepository;

@Service
@RequiredArgsConstructor
public class EnterpriseServiceReportService {

    private final ReportRepository reportRepository;

    public Optional<EnterpriseServiceReport> getByReportId(long reportId) {
        return reportRepository.findById(reportId).map(EnterpriseServiceReport::fromEntity);
    }

}
