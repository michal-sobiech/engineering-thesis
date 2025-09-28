package pl.michal_sobiech.engineering_thesis.enterprise;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

// TODO autogenerate these classes?
@Entity
@Data
@Builder
public class Enterprise {

    @Id
    @Column(name = "enterprise_id")
    private final long enterpriseId;

    @Column(name = "enterpreneur_id")
    private final long enterpreneurId;

    @Column
    private final String name;

    @Column
    private final String description;

    // TODO max String length?
    @Column
    private final String location;

}
