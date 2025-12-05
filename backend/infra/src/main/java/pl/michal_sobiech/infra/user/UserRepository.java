package pl.michal_sobiech.shared.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pl.michal_sobiech.core.user.UserEntity;
import pl.michal_sobiech.core.user.UserGroup;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    public Optional<UserEntity> findByEnterpriseIdAndUsername(long enterpriseId, String username);

    public Optional<UserEntity> findByUserGroupInAndUsername(List<UserGroup> userGroups, String username);

    public Optional<UserEntity> findByUserGroupAndUsername(UserGroup userGroup, String username);

    public List<UserEntity> findAllByUserGroup(UserGroup userGroup);

    @Query("""
            SELECT DISTINCT u
            FROM UserEntity u
            JOIN EnterpriseEntity e ON e.ownerUserId = u.userId
            JOIN EnterpriseServiceEntity s ON s.enterpriseId = e.enterpriseId
            JOIN AppointmentEntity a ON a.enterpriseServiceId = s.enterpriseServiceId
            WHERE a.appointmentId = :appointmentId
            """)
    public Optional<UserEntity> getEnterpriseOwner(@Param("appointmentId") long appointmentId);

    @Query("""
            SELECT DISTINCT u
            FROM UserEntity u
            JOIN EnterpriseEntity e ON e.enterpriseId = u.enterpriseId
            JOIN EnterpriseServiceEntity s ON s.enterpriseId = e.enterpriseId
            JOIN AppointmentEntity a ON a.enterpriseServiceId = s.enterpriseServiceId
            WHERE a.appointmentId = :appointmentId
            """)
    public List<UserEntity> getEnterpriseEmployees(@Param("appointmentId") long appointmentId);

}
