package pl.michal_sobiech.core.user;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    public UserEntity save(UserEntity record);

    public Optional<UserEntity> findById(long userId);

    public Optional<UserEntity> findByEnterpriseIdAndUsername(long enterpriseId, String username);

    public Optional<UserEntity> findByUserGroupInAndUsername(List<UserGroup> userGroups, String username);

    public Optional<UserEntity> findByUserGroupAndUsername(UserGroup userGroup, String username);

    public List<UserEntity> findAllByUserGroup(UserGroup userGroup);

    public Optional<UserEntity> getEnterpriseOwner(long appointmentId);

    public List<UserEntity> getEnterpriseEmployees(long appointmentId);

}
