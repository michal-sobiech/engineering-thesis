package pl.michal_sobiech.core.report;

import java.util.List;
import java.util.Optional;

public interface ReportRepository {

    public ReportEntity save(ReportEntity record);

    public List<GetUnresolvedReportsRow> findDetailedUnresolvedReports();

    public Optional<GetUnresolvedReportsRow> findDetailedUnresolvedReportByReportId(
            long reportId);

    public int resolveReport(long reportId);

}
