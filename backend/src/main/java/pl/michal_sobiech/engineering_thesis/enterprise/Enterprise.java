package pl.michal_sobiech.engineering_thesis.enterprise;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO autogenerate these classes?
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Enterprise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enterprise_id")
    private long enterpriseId;

    @Column(name = "entrepreneur_id")
    private long entrepreneurId;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String location;

    @Column(name = "logo_photo_id")
    private Long logoPhotoId;

    @Column(name = "background_photo_id")
    private Long backgroundPhotoId;

}
