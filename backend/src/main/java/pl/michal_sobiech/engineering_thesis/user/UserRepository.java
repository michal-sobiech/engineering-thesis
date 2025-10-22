package pl.michal_sobiech.engineering_thesis.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByEnterpriseIdAndUsername(long entepriseId, String username);

    public Optional<User> findByUserGroupInAndUsername(List<UserGroup> userGroups, String username);

    public Optional<User> findByUserGroupAndUsername(UserGroup userGroup, String username);

}
