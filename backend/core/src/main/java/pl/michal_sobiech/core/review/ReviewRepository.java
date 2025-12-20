package pl.michal_sobiech.core.review;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository {

    public ReviewEntity save(ReviewEntity record);

    public Optional<ReviewEntity> findById(long reviewId);

    public List<ReviewEntity> findAllBySubjectEnterpriseServiceId(long subjectEnterpriseServiceId);

}
