package pl.michal_sobiech.engineering_thesis.report;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pl.michal_sobiech.core.report.ReportFactory;
import pl.michal_sobiech.core.report.ReportRepository;
import pl.michal_sobiech.core.report.ReportService;
import pl.michal_sobiech.core.report.enterprise.EnterpriseReportFactory;
import pl.michal_sobiech.core.report.enterprise.EnterpriseReportService;
import pl.michal_sobiech.core.report.enterprise.UnresolvedEnterpriseReportService;
import pl.michal_sobiech.core.report.enterprise_service.EnterpriseServiceReportFactory;
import pl.michal_sobiech.core.report.enterprise_service.EnterpriseServiceReportService;
import pl.michal_sobiech.core.report.enterprise_service.UnresolvedEnterpriseServiceReportService;
import pl.michal_sobiech.core.report.review.ReviewReportFactory;
import pl.michal_sobiech.core.report.review.ReviewReportService;
import pl.michal_sobiech.core.report.review.UnresolvedReviewReportService;
import pl.michal_sobiech.core.report.unresolved.UnresolvedReportService;

@Configuration
public class ReportConfig {

    @Bean
    public ReportService reportService(ReportRepository reportRepository) {
        return new ReportService(reportRepository);
    }

    @Bean
    public EnterpriseReportService enterpriseReportService(
            ReportRepository reportRepository,
            EnterpriseReportFactory enterpriseReportFactory) {
        return new EnterpriseReportService(reportRepository, enterpriseReportFactory);
    }

    @Bean
    public UnresolvedEnterpriseReportService unresolvedEnterpriseReportService(
            ReportRepository reportRepository,
            EnterpriseReportFactory enterpriseReportFactory) {
        return new UnresolvedEnterpriseReportService(reportRepository, enterpriseReportFactory);
    }

    @Bean
    public EnterpriseServiceReportService enterpriseServiceReportService(
            ReportRepository reportRepository,
            EnterpriseServiceReportFactory enterpriseServiceReportFactory) {
        return new EnterpriseServiceReportService(reportRepository, enterpriseServiceReportFactory);
    }

    @Bean
    public UnresolvedEnterpriseServiceReportService unresolvedEnterpriseServiceReportService(
            ReportRepository reportRepository,
            EnterpriseServiceReportFactory enterpriseServiceReportFactory) {
        return new UnresolvedEnterpriseServiceReportService(reportRepository, enterpriseServiceReportFactory);
    }

    @Bean
    public ReviewReportService reviewReportService(
            ReportRepository reportRepository,
            ReviewReportFactory reviewReportFactory) {
        return new ReviewReportService(reportRepository, reviewReportFactory);
    }

    @Bean
    public UnresolvedReviewReportService unresolvedReviewReportService(
            ReportRepository reportRepository,
            ReviewReportFactory reviewReportFactory) {
        return new UnresolvedReviewReportService(reportRepository, reviewReportFactory);
    }

    @Bean
    public UnresolvedReportService unresolvedReportService(
            ReportRepository reportRepository,
            ReportFactory reportFactory) {
        return new UnresolvedReportService(reportRepository, reportFactory);
    }

}
