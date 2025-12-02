package pl.michal_sobiech.engineering_thesis.report;

import pl.michal_sobiech.shared.user.UserGroup;

public interface UnresolvedReport {

    public long reportId();

    public long creatorUserId();

    public UserGroup creatorUserGroup();

    public String creatorUsername();

}
