package pl.michal_sobiech.core.regular_admin;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.core.user.UserGroup;
import pl.michal_sobiech.core.user.UserService;

@RequiredArgsConstructor
public class RegularAdminsService {

    private final UserService userService;

    public Optional<RegularAdmin> getRegularAdmin(long userId) {
        return userService.findById(userId).map(RegularAdmin::fromUser);
    }

    public List<RegularAdmin> getRegularAdmins() {
        return userService.getAllInUserGroup(UserGroup.REGULAR_ADMIN)
                .stream()
                .map(RegularAdmin::fromUser)
                .collect(Collectors.toList());
    }

    public void createRegularAdmin(
            String username,
            String firstName,
            String lastName,
            String passwordRaw) {
        userService.save(
                UserGroup.REGULAR_ADMIN,
                username,
                firstName,
                lastName,
                passwordRaw,
                Optional.empty(),
                Optional.empty());
    }

}
