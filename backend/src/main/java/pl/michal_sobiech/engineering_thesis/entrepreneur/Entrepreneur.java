package pl.michal_sobiech.engineering_thesis.entrepreneur;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class Entrepreneur {

    @Column(name = "entrepreneur_id")
    private final long entrepreneurId;

    @Column(name = "independent_end_user_id")
    private final long independentEndUserId;

}
