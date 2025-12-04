package pl.michal_sobiech.engineering_thesis.report.enterprise;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.enterprise.Enterprise;
import pl.michal_sobiech.engineering_thesis.enterprise.EnterpriseService;
import pl.michal_sobiech.engineering_thesis.report.GetUnresolvedReportsRow;

@Component
@RequiredArgsConstructor
public class EnterpriseReportFactory {

    private final EnterpriseService enterpriseService;

    public EnterpriseReport fromRecord(GetUnresolvedReportsRow record) {
        if (record.getEnterpriseId() == null
                || record.getEnterpriseServiceId() != null
                || record.getReviewId() != null) {
            throw new IllegalArgumentException();
        }

        Enterprise enterprise = enterpriseService.getEnterprise(record.getEnterpriseId()).orElseThrow();

        return new EnterpriseReport(
                record.getReportId(),
                record.getCreatorUserId(),
                record.getCreatorUserGroup(),
                record.getCreatorUsername(),
                record.getEnterpriseId(),
                enterprise.name(),
                record.getIsResolved());
    }

}
