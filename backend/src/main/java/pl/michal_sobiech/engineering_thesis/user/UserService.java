package pl.michal_sobiech.engineering_thesis.user;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> findById(long userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> findByEnterpriseIdAndUsername(long enterpriseId, String username) {
        return userRepository.findByEnterpriseIdAndUsername(enterpriseId, username);
    }

    public Optional<User> findIndepentendEndUserByEmail(String email) {
        return userRepository.findByUsernameNamespaceAndUsername(UsernameNamespace.EMAIL, email);
    }

    public Optional<User> findAdminByUsername(String username) {
        return userRepository.findByUsernameNamespaceAndUsername(UsernameNamespace.ADMIN, username);
    }

}
