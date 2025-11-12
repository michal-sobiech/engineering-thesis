package pl.michal_sobiech.engineering_thesis.report;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReportRepository extends JpaRepository<ReportEntity, Long> {

        @Query("""
                        SELECT
                        r.reportId AS reportId,
                        u.userGroup AS creatorUserGroup,
                        r.creatorUserId AS creatorUserId,
                        u.username AS creatorUsername,
                        r.enterpriseId AS enterpriseId,
                        r.enterpriseServiceId AS enterpriseServiceId,
                        r.reviewId AS reviewId,
                        r.isResolved AS isResolved
                        FROM ReportEntity r
                        JOIN UserEntity u on u.userId = r.creatorUserId
                        WHERE r.isResolved = FALSE
                        """)
        public List<GetUnresolvedReportsRow> findDetailedUnresolvedReports();

        @Query("""
                        SELECT
                        r.reportId AS reportId,
                        u.userGroup AS creatorUserGroup,
                        r.creatorUserId AS creatorUserId,
                        u.username AS creatorUsername,
                        r.enterpriseId AS enterpriseId,
                        r.enterpriseServiceId AS enterpriseServiceId,
                        r.reviewId AS reviewId,
                        r.isResolved AS isResolved
                        FROM ReportEntity r
                        JOIN UserEntity u on u.userId = r.creatorUserId
                        WHERE r.isResolved = FALSE
                        AND r.reportId = :reportId
                        """)
        public Optional<GetUnresolvedReportsRow> findDetailedUnresolvedReportByReportId(
                        @Param("reportId") long reportId);

        @Query("""
                        UPDATE ReportEntity r
                        SET r.isResolved = TRUE
                        WHERE r.reportId = :reportId
                        AND r.isResolved = FALSE
                        """)
        public int resolveReport(@Param("reportId") long reportId);

}
