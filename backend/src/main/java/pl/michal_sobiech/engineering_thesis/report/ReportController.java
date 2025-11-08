package pl.michal_sobiech.engineering_thesis.report;

import org.SwaggerCodeGenExample.api.ReportApi;
import org.SwaggerCodeGenExample.model.CreateReportRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.auth.AuthService;
import pl.michal_sobiech.engineering_thesis.user.User;

@RestController
@RequiredArgsConstructor
public class ReportController implements ReportApi {

    private final ReportService reportService;
    private final AuthService authService;

    @Override
    public ResponseEntity<Void> createReport(CreateReportRequest request) {
        User authorizedUser = authService.requireAuthorizedUser();

        ReportSubject reportSubject = ReportSubject.valueOf(request.getReportSubjectType());

        reportService.createReport(
                authorizedUser.getUserId(),
                reportSubject,
                request.getReportSubjectId().longValue());

        return ResponseEntity.ok().build();
    }

}
