package pl.michal_sobiech.engineering_thesis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.dto.IndependentEndUserCreateDTO;
import pl.michal_sobiech.engineering_thesis.model.IndependentEndUser;
import pl.michal_sobiech.engineering_thesis.repository.IndependentEndUserRepository;

@Service
@RequiredArgsConstructor
public class IndependentEndUserService {

    IndependentEndUserRepository independentEndUserRepository;

    @Transactional
    public IndependentEndUser createIndependentEndUser(IndependentEndUserCreateDTO dto) {
        return new IndependentEndUser(dto);
    }

}
