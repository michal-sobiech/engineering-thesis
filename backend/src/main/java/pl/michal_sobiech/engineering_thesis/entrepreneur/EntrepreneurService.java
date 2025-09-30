package pl.michal_sobiech.engineering_thesis.entrepreneur;

import java.util.Optional;

import org.SwaggerCodeGenExample.model.CreateIndependentEndUserRequest;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.independent_end_user.IndependentEndUser;
import pl.michal_sobiech.engineering_thesis.independent_end_user.IndependentEndUserService;

@Service
@RequiredArgsConstructor
public class EntrepreneurService {

    private final IndependentEndUserService independentEndUserService;
    private final EntrepreneurRepository entrepreneurRepository;

    @Transactional
    public Entrepreneur createEntrepreneur(CreateIndependentEndUserRequest request) {
        IndependentEndUser independentEndUser = independentEndUserService.createIndependentEndUser(request);

        Entrepreneur entrepreneur = Entrepreneur.builder()
                .independentEndUserId(independentEndUser.getIndependentEndUserId())
                .build();
        entrepreneur = entrepreneurRepository.save(entrepreneur);

        return entrepreneur;
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "entrepreneurs", key = "#id")
    public Optional<Entrepreneur> findByEntrepreneurId(long entrepreneurId) {
        return entrepreneurRepository.findById(entrepreneurId);
    }

    @Transactional
    public boolean existsByIndependentEndUserId(long independentEndUserId) {
        return entrepreneurRepository.existsByIndependentEndUserId(independentEndUserId);
    }

    @Transactional
    public Optional<Long> findEntrepreneurIdByIndependentEndUserId(long independentEndUserId) {
        return entrepreneurRepository.findEntrepreneurIdByIndependentEndUserId(independentEndUserId);
    }

    @Transactional
    public Optional<Entrepreneur> findByUserId(long userId) {
        return entrepreneurRepository.findByUserId(userId);
    }

}
