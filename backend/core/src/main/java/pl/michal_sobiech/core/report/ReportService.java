package pl.michal_sobiech.core.report;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    public void createReport(
            long creatorUserId,
            ReportSubjectType reportSubjectType,
            long reportSubjectId,
            String content) {
        var builder = ReportEntity.builder()
                .creatorUserId(creatorUserId)
                .content(content);

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
