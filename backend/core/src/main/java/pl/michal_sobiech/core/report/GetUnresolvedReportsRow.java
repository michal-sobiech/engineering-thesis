package pl.michal_sobiech.core.report;

import org.springframework.lang.Nullable;

import pl.michal_sobiech.shared.user.UserGroup;

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
