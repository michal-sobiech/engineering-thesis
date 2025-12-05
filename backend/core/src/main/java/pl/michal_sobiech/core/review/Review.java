package pl.michal_sobiech.core.review;

public record Review(

        long reviewId,

        long creatorCustomerUserId,

        long subjectEnterpriseServiceId,

        short startOutOf5,

        String content,

        boolean suspendedByAdmin

) {

    public static Review fromEntity(ReviewEntity entity) {
        return new Review(
                entity.getReviewId(),
                entity.getCreatorCustomerUserId(),
                entity.getSubjectEnterpriseServiceId(),
                entity.getStartOutOf5(),
                entity.getContent(),
                entity.isSuspendedByAdmin());
    }

}
