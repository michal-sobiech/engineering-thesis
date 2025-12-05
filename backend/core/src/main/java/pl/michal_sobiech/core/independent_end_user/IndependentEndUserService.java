package pl.michal_sobiech.core.independent_end_user;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.user.User;
import pl.michal_sobiech.shared.user.UserRepository;

@RequiredArgsConstructor
public class IndependentEndUserService {

    private final UserRepository userRepository;

    public Optional<IndependentEndUser> findByEmail(String email) {
        return userRepository.findByUserGroupInAndUsername(IndependentEndUserUtils.USER_GROUPS, email)
                .map(User::fromEntity)
                .map(IndependentEndUser::fromUser);
    }
}
