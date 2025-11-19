package pl.michal_sobiech.engineering_thesis.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    public Optional<UserEntity> findByEnterpriseIdAndUsername(long enterpriseId, String username);

    public Optional<UserEntity> findByUserGroupInAndUsername(List<UserGroup> userGroups, String username);

    public Optional<UserEntity> findByUserGroupAndUsername(UserGroup userGroup, String username);

    public List<UserEntity> findAllByUserGroup(UserGroup userGroup);

}
