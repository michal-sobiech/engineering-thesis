package pl.michal_sobiech.core.report;

import pl.michal_sobiech.core.user.UserGroup;

public interface UnresolvedReport {

    public long reportId();

    public long creatorUserId();

    public UserGroup creatorUserGroup();

    public String creatorUsername();

}
