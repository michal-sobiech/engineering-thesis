package pl.michal_sobiech.engineering_thesis.independent_end_user;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IndependentEndUserService {

    private final IndependentEndUserRepository independentEndUserRepository;

    @Transactional
    public IndependentEndUser createIndependentEndUser(CreateIndependentEndUserRequest request) {
        // TODO
        String passwordHash = "...";

        IndependentEndUser independentEndUser = IndependentEndUser.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .passwordHash(passwordHash)
                .build();
        independentEndUser = independentEndUserRepository.save(independentEndUser);

        return independentEndUser;
    }

}
