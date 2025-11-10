export const reportSubjectTypes = ["ENTERPRISE", "SERVICE", "REVIEW"] as const;
export type ReportSubjectType = (typeof reportSubjectTypes)[number];

export type AdminLandingPageUnresolvedEnterpriseReport = AdminLandingPageUnresolvedReportBase & {
    subjectType: "ENTERPRISE",
    enterpriseId: number;
    enterpriseName: string;
};

export type AdminLandingPageUnresolvedServiceReport = AdminLandingPageUnresolvedReportBase & {
    subjectType: "SERVICE",
    serviceId: number;
    serviceName: string;
};

export type AdminLandingPageUnresolvedReviewReport = AdminLandingPageUnresolvedReportBase & {
    subjectType: "REVIEW",
    reviewId: number;
    reviewText: string;
};

export type AdminLandingPageUnresolvedReport = AdminLandingPageUnresolvedEnterpriseReport | AdminLandingPageUnresolvedServiceReport | AdminLandingPageUnresolvedReviewReport;

interface AdminLandingPageUnresolvedReportBase {
    reportId: number;

    reportingUserType: string,
    reportingUserId: number,
    reportingUserUsername: string;
}