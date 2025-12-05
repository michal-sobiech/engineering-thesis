package pl.michal_sobiech.infra.report;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.report.GetUnresolvedReportsRow;
import pl.michal_sobiech.core.report.ReportEntity;
import pl.michal_sobiech.core.report.ReportRepository;

@Component
@RequiredArgsConstructor
public class ReportRepositoryImpl implements ReportRepository {

    private final SpringReportRepository springReportRepository;

    @Override
    public ReportEntity save(ReportEntity reportEntity) {
        return springReportRepository.save(reportEntity);
    }

    @Override
    public List<GetUnresolvedReportsRow> findDetailedUnresolvedReports() {
        return springReportRepository.findDetailedUnresolvedReports();
    }

    @Override
    public Optional<GetUnresolvedReportsRow> findDetailedUnresolvedReportByReportId(
            long reportId) {
        return springReportRepository.findDetailedUnresolvedReportByReportId(reportId);
    }

    @Override
    public int resolveReport(long reportId) {
        return springReportRepository.resolveReport(reportId);
    }

}
