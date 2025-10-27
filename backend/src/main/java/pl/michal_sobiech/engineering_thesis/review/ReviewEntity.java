package pl.michal_sobiech.engineering_thesis.review;

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
@Table(name = "review")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private long reviewId;

    @Column(name = "creator_customer_user_id")
    private long creatorCustomerUserId;

    @Column(name = "subject_enterprise_id")
    private long subjectEnterpriseId;

    @Column(name = "stars_out_of_5")
    private short startOutOf5;

    @Column(nullable = true)
    private String content;

    @Column(name = "hidden_by_admin")
    private boolean hiddenByAdmin;

}
