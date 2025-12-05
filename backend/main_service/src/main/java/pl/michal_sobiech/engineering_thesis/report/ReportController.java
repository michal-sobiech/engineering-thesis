package pl.michal_sobiech.engineering_thesis.report;

import java.util.List;
import java.util.stream.Collectors;

import org.SwaggerCodeGenExample.api.ReportsApi;
import org.SwaggerCodeGenExample.model.CreateReportRequest;
import org.SwaggerCodeGenExample.model.GetUnresolvedReports200ResponseInner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.report.ReportService;
import pl.michal_sobiech.core.report.ReportSubjectType;
import pl.michal_sobiech.core.report.unresolved.UnresolvedReportService;
import pl.michal_sobiech.core.user.User;
import pl.michal_sobiech.engineering_thesis.auth.AuthService;
import pl.michal_sobiech.engineering_thesis.report.unresolved.GetUnresolvedReports200ResponseInnerFactory;

@RestController
@RequiredArgsConstructor
public class ReportController implements ReportsApi {

    private final ReportService reportService;
    private final AuthService authService;
    private final UnresolvedReportService unresolvedReportService;

    @Override
    public ResponseEntity<Void> createReport(CreateReportRequest request) {
        User authorizedUser = authService.requireAuthorizedUser();

        ReportSubjectType reportSubject = ReportSubjectType.valueOf(request.getReportSubjectType());

        reportService.createReport(
                authorizedUser.getUserId(),
                reportSubject,
                request.getReportSubjectId().longValue());

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> resolveReport(Long reportId) {
        authService.requireAdmin();
        unresolvedReportService.resolveReport(reportId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<GetUnresolvedReports200ResponseInner>> getUnresolvedReports() {
        var body = unresolvedReportService.getUnresolvedReports()
                .stream()
                .map(GetUnresolvedReports200ResponseInnerFactory::fromUnresolvedReport)
                .collect(Collectors.toList());
        return ResponseEntity.ok(body);
    }
}
