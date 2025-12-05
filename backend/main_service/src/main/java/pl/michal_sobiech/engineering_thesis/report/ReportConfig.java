package pl.michal_sobiech.engineering_thesis.report;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pl.michal_sobiech.core.enterprise.EnterpriseService;
import pl.michal_sobiech.core.enterprise_service.EnterpriseServiceService;
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
import pl.michal_sobiech.core.review.ReviewService;

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

    @Bean
    public ReportFactory reportFactory(
            EnterpriseReportFactory enterpriseReportFactory,
            EnterpriseServiceReportFactory enterpriseServiceReportFactory,
            ReviewReportFactory reviewReportFactory) {
        return new ReportFactory(enterpriseReportFactory, enterpriseServiceReportFactory, reviewReportFactory);
    }

    @Bean
    public EnterpriseReportFactory enterpriseReportFactory(EnterpriseService enterpriseService) {
        return new EnterpriseReportFactory(enterpriseService);
    }

    @Bean
    public EnterpriseServiceReportFactory enterpriseServiceReportFactory(
            EnterpriseServiceService enterpriseServiceService) {
        return new EnterpriseServiceReportFactory(enterpriseServiceService);
    }

    @Bean
    public ReviewReportFactory reviewReportFactory(ReviewService reviewService) {
        return new ReviewReportFactory(reviewService);
    }

}
