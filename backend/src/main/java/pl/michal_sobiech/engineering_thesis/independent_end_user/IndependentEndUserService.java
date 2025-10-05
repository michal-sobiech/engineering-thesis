package pl.michal_sobiech.engineering_thesis.independent_end_user;

import java.util.Optional;

import org.SwaggerCodeGenExample.model.CreateIndependentEndUserRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IndependentEndUserService {

    private final IndependentEndUserRepository independentEndUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public IndependentEndUser createIndependentEndUser(CreateIndependentEndUserRequest request) {
        String passwordHash = passwordEncoder.encode(request.getPassword());

        IndependentEndUser independentEndUser = IndependentEndUser.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .passwordHash(passwordHash)
                .build();
        independentEndUser = independentEndUserRepository.save(independentEndUser);

        return independentEndUser;
    }

    @Transactional
    public boolean checkIndependentEndUserEmailExists(String email) {
        return independentEndUserRepository.existsByEmail(email);
    }

    @Transactional
    public Optional<IndependentEndUser> findByUsername(String username) {
        return independentEndUserRepository.findByEmail(username);
    }

    @Transactional
    public Optional<IndependentEndUser> findByIndependentEndUserId(long independentEndUserId) {
        return independentEndUserRepository.findById(independentEndUserId);
    }

    @Transactional
    public Optional<IndependentEndUser> findByUserId(long userId) {
        return independentEndUserRepository.findByUserId(userId);
    }

}
