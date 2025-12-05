package pl.michal_sobiech.core.report;

import pl.michal_sobiech.shared.user.UserGroup;

public interface Report {

    public long reportId();

    public long creatorUserId();

    public UserGroup creatorUserGroup();

    public String creatorUsername();

    public boolean isResolved();

}
