package pl.michal_sobiech.core.report;

import jakarta.annotation.Nullable;
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
    private Long reportId;

    @Column(name = "creator_user_id")
    private Long creatorUserId;

    @Nullable
    @Column(name = "enterprise_id", nullable = true)
    private Long enterpriseId;

    @Nullable
    @Column(name = "enterprise_service_id", nullable = true)
    private Long enterpriseServiceId;

    @Nullable
    @Column(name = "review_id", nullable = true)
    private Long reviewId;

    @Column(name = "is_resolved")
    private boolean isResolved;

    private String content;

}
