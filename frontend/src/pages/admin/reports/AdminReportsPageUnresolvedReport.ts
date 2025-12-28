export const reportSubjectTypes = ["ENTERPRISE", "SERVICE", "REVIEW"] as const;
export type ReportSubjectType = (typeof reportSubjectTypes)[number];

export type AdminReportsPageUnresolvedEnterpriseReport = AdminReportsPageUnresolvedReportBase & {
    subjectType: "ENTERPRISE",
    enterpriseId: number;
    enterpriseName: string;

};

export type AdminReportsPageUnresolvedServiceReport = AdminReportsPageUnresolvedReportBase & {
    subjectType: "SERVICE",
    serviceId: number;
    serviceName: string;
};

export type AdminReportsPageUnresolvedReviewReport = AdminReportsPageUnresolvedReportBase & {
    subjectType: "REVIEW",
    reviewId: number;
    reviewText: string;
};

export type AdminReportsPageUnresolvedReport = AdminReportsPageUnresolvedEnterpriseReport | AdminReportsPageUnresolvedServiceReport | AdminReportsPageUnresolvedReviewReport;

interface AdminReportsPageUnresolvedReportBase {
    reportId: number;

    content: string;

    reportingUserType: string,
    reportingUserId: number,
    reportingUserUsername: string;
}