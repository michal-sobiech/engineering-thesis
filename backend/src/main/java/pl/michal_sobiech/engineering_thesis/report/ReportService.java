package pl.michal_sobiech.engineering_thesis.report;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    public void createReport(
            long creatorUserId,
            ReportSubject reportSubjectType,
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

}
