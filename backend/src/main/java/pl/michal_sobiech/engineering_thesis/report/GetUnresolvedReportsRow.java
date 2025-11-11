package pl.michal_sobiech.engineering_thesis.report;

import org.springframework.lang.Nullable;

import pl.michal_sobiech.engineering_thesis.user.UserGroup;

public interface GetUnresolvedReportsRow {

    public long getReportId();

    public UserGroup getCreatorUserGroup();

    public long getCreatorUserId();

    public String getCreatorUsername();

    @Nullable
    public Long getEnterpriseId();

    @Nullable
    public Long getEnterpriseServiceId();

    @Nullable
    public Long getReviewId();

    public boolean getIsResolved();

}
