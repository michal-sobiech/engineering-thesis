package pl.michal_sobiech.engineering_thesis.report;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.report.enterprise.EnterpriseReport;
import pl.michal_sobiech.engineering_thesis.report.enterprise_service.EnterpriseServiceReport;
import pl.michal_sobiech.engineering_thesis.report.review.ReviewReport;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    public void createReport(
            long creatorUserId,
            ReportSubjectType reportSubjectType,
            long reportSubjectId) {
        var builder = ReportEntity.builder()
                .creatorUserId(creatorUserId);

        switch (reportSubjectType) {
            case ENTERPRISE -> {
                builder.enterpriseId(reportSubjectId);
            }
            case ENTERPRISE_SERVICE -> {
                builder.enterpriseServiceId(reportSubjectId);
            }
            case REVIEW -> {
                builder.reviewId(reportSubjectId);
            }
        }

        ReportEntity report = builder.build();
        reportRepository.save(report);
    }

    public ReportSubjectType getReportSubjectType(long reportId) {
        Report report = getById(reportId).orElseThrow();
        return switch (report) {
            case EnterpriseReport _ -> ReportSubjectType.ENTERPRISE;
            case EnterpriseServiceReport _ -> ReportSubjectType.ENTERPRISE_SERVICE;
            case ReviewReport _ -> ReportSubjectType.REVIEW;
            default -> throw new UnsupportedOperationException();
        };
    }

    public Optional<? extends Report> getById(long reportId) {
        return reportRepository.findById(reportId).map(ReportFactory::fromEntity);
    }

    public void resolveReport(long reportId) {
        // TODO
    }

}
