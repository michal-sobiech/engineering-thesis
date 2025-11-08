package pl.michal_sobiech.engineering_thesis.enterprise;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "enterprise")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnterpriseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enterprise_id")
    private long enterpriseId;

    @Column(name = "owner_user_id")
    private long ownerUserId;

    private String name;
    private String description;

    @Column(nullable = true)
    private String address;

    @Column(nullable = true)
    private double latitude;

    @Column(nullable = true)
    private double longitude;

    @Column(name = "logo_file_name", nullable = true)
    private String logoFileName;

    @Column(name = "logo_file_bytes", nullable = true)
    private byte[] logoFileBytes;

    @Column(name = "background_photo_file_name", nullable = true)
    private String backgroundPhotoFileName;

    @Column(name = "background_photo_file_bytes", nullable = true)
    private byte[] backgroundPhotoFileBytes;

    @Column(name = "suspended_by_admin")
    private boolean suspendedByAdmin;

}
