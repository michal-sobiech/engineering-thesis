package pl.michal_sobiech.engineering_thesis.entrepreneur;

import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.dto.create_entrepreneur.CreateEntrepreneurRequest;
import pl.michal_sobiech.engineering_thesis.model.IndependentEndUser;
import pl.michal_sobiech.engineering_thesis.repository.IndependentEndUserRepository;

@Service
@RequiredArgsConstructor
public class EntrepreneurService {

    private final IndependentEndUserRepository independentEndUserRepository;
    private final EntrepreneurRepository entrepreneurRepository;

    @Transactional
    public Entrepreneur createEntrepreneur(CreateEntrepreneurRequest request) {
        IndependentEndUser independentEndUser = IndependentEndUser.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .passwordHash(request.passwordHash())
                .build();
        independentEndUser = independentEndUserRepository.save(independentEndUser);

        Entrepreneur entrepreneur = Entrepreneur.builder()
                .independentEndUser(independentEndUser)
                .build();
        entrepreneur = entrepreneurRepository.save(entrepreneur);

        return entrepreneur;
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "entrepreneurs", key = "#id")
    public Optional<Entrepreneur> getEnrepreneur(long id) {
        return entrepreneurRepository.findById(id);
    }

}
