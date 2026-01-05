package pl.michal_sobiech.infra.user;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.user.UserEntity;
import pl.michal_sobiech.core.user.UserGroup;
import pl.michal_sobiech.core.user.UserRepository;

@Component
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final SpringUserRepository springUserRepository;

    @Override
    public UserEntity save(UserEntity record) {
        return springUserRepository.save(record);
    }

    @Override
    public Optional<UserEntity> findById(long userId) {
        return springUserRepository.findById(userId);
    }

    @Override
    public Optional<UserEntity> findByEnterpriseIdAndUsername(long enterpriseId, String username) {
        return springUserRepository.findByEnterpriseIdAndUsername(enterpriseId, username);
    }

    @Override
    public Optional<UserEntity> findByUserGroupInAndUsername(List<UserGroup> userGroups, String username) {
        return springUserRepository.findByUserGroupInAndUsername(userGroups, username);
    }

    @Override
    public Optional<UserEntity> findByUserGroupAndUsername(UserGroup userGroup, String username) {
        return springUserRepository.findByUserGroupAndUsername(userGroup, username);
    }

    @Override
    public List<UserEntity> findAllByUserGroup(UserGroup userGroup) {
        return springUserRepository.findAllByUserGroup(userGroup);
    }

    @Override
    public Optional<UserEntity> getEnterpriseOwner(long appointmentId) {
        return springUserRepository.getEnterpriseOwner(appointmentId);
    }

    @Override
    public List<UserEntity> getEnterpriseEmployeesByAppointmentId(long appointmentId) {
        return springUserRepository.getEnterpriseEmployeesByAppointmentId(appointmentId);
    }

    @Override
    public List<UserEntity> getEnterpriseEmployeesByEnterpriseId(long enterpriseId) {
        return springUserRepository.getEnterpriseEmployeesByEnterpriseId(enterpriseId);
    }

}
