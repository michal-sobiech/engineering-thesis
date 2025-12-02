package pl.michal_sobiech.engineering_thesis.independent_end_user;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.shared.user.User;
import pl.michal_sobiech.shared.user.UserRepository;

@Service
@RequiredArgsConstructor
public class IndependentEndUserService {

    private final UserRepository userRepository;

    public Optional<IndependentEndUser> findByEmail(String email) {
        return userRepository.findByUserGroupInAndUsername(IndependentEndUserUtils.USER_GROUPS, email)
                .map(User::fromEntity)
                .map(IndependentEndUser::fromUser);
    }
}
