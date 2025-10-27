package pl.michal_sobiech.engineering_thesis.report;

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
@Table(name = "report")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private long reportId;

    @Column(name = "creator_user_id")
    private long creatorUserId;

    @Column(name = "enterprise_id", nullable = true)
    private long enterpriseId;

    @Column(name = "enterprise_service_id", nullable = true)
    private long enterpriseServiceId;

    @Column(name = "review_id", nullable = true)
    private long reviewId;

}
