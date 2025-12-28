package pl.michal_sobiech.core.report;

import jakarta.annotation.Nullable;
import pl.michal_sobiech.core.user.UserGroup;

public interface GetUnresolvedReportsRow {

    public long getReportId();

    public String getContent();

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
