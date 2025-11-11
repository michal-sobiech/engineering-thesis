import { Box, Center } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import { reportsApi } from "../../../../api/reports-api";
import { GetUnresolvedReports200ResponseInner } from "../../../../GENERATED-api";
import { routes } from "../../../../router/routes";
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../../../../utils/error";
import { errorErrResultAsyncFromPromise } from "../../../../utils/result";
import { toastError } from "../../../../utils/toast";
import { AdminLandingPageContext, AdminLandingPageContextValue } from "./AdminLandingPageContext";
import { AdminLandingPageUnresolvedEnterpriseReportsList } from "./AdminLandingPageUnresolvedEnterpriseReportsList";
import { AdminLandingPageUnresolvedEnterpriseReport, AdminLandingPageUnresolvedReport, AdminLandingPageUnresolvedReviewReport, AdminLandingPageUnresolvedServiceReport } from "./AdminLandingPageUnresolvedReport";
import { AdminLandingPageUnresolvedReviewReportsList } from "./AdminLandingPageUnresolvedReviewReportsList";
import { AdminLandingPageUnresolvedServiceReportsList } from "./AdminLandingPageUnresolvedServiceReportsList";

export const AdminLandingPage = () => {
    const [unresolvedEnterpriseReports, setUnresolvedEnterpriseReports] = useState<AdminLandingPageUnresolvedEnterpriseReport[]>([]);
    const [unresolvedServiceReports, setUnresolvedServiceReports] = useState<AdminLandingPageUnresolvedServiceReport[]>([]);
    const [unresolvedReviewReports, setUnresolvedReviewReports] = useState<AdminLandingPageUnresolvedReviewReport[]>([]);

    const navigate = useNavigate();

    const contextValue: AdminLandingPageContextValue = {
        unresolvedEnterpriseReports,
        setUnresolvedEnterpriseReports,
        unresolvedServiceReports,
        setUnresolvedServiceReports,
        unresolvedReviewReports,
        setUnresolvedReviewReports,
    };

    useEffect(() => {
        async function loadUnresolvedReports() {
            const promise = reportsApi.getUnresolvedReports();
            const resultAsync = errorErrResultAsyncFromPromise(promise);
            const result = await resultAsync;
            if (result.isErr()) {
                toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
                throw navigate(routes.mainPage);
            }

            const reports = result.value;
            const reportsDomain = reports.map(createReportDomain);
            const reportGroups = splitReportsIntoGroups(reportsDomain);

            setUnresolvedEnterpriseReports(reportGroups.ENTERPRISE);
            setUnresolvedServiceReports(reportGroups.SERVICE);
            setUnresolvedReviewReports(reportGroups.REVIEW);
        }
        loadUnresolvedReports();
    }, [])

    return <AdminLandingPageContext.Provider value={contextValue}>
        <Center height="100vh">
            <Box width="80vw" height="100%">
                <AdminLandingPageUnresolvedEnterpriseReportsList />
                <AdminLandingPageUnresolvedServiceReportsList />
                <AdminLandingPageUnresolvedReviewReportsList />
            </Box>
        </Center>
    </AdminLandingPageContext.Provider>
}

function createReportDomain(reportDto: GetUnresolvedReports200ResponseInner): AdminLandingPageUnresolvedReport {
    if (reportDto.reportSubjectType === "ENTERPRISE") {
        return {
            reportId: reportDto.reportId,

            reportingUserType: reportDto.reportingUserType,
            reportingUserId: reportDto.reportingUserId,
            reportingUserUsername: reportDto.reportingUserUsername,

            subjectType: reportDto.reportSubjectType,
            enterpriseId: reportDto.enterpriseId,
            enterpriseName: reportDto.enterpriseName,
        } satisfies AdminLandingPageUnresolvedEnterpriseReport;
    } else if (reportDto.reportSubjectType === "SERVICE") {
        return {
            reportId: reportDto.reportId,

            reportingUserType: reportDto.reportingUserType,
            reportingUserId: reportDto.reportingUserId,
            reportingUserUsername: reportDto.reportingUserUsername,

            subjectType: reportDto.reportSubjectType,
            serviceId: reportDto.enterpriseServiceId,
            serviceName: reportDto.enterpriseServiceName,
        } satisfies AdminLandingPageUnresolvedServiceReport;
    } else if (reportDto.reportSubjectType === "REVIEW") {
        return {
            reportId: reportDto.reportId,

            reportingUserType: reportDto.reportingUserType,
            reportingUserId: reportDto.reportingUserId,
            reportingUserUsername: reportDto.reportingUserUsername,

            subjectType: reportDto.reportSubjectType,
            reviewId: reportDto.reviewId,
            reviewText: reportDto.reviewText,
        } satisfies AdminLandingPageUnresolvedReviewReport;
    } else {
        throw new Error("Invalid argument, couldn't match with any supported report subject type");
    }
}

function splitReportsIntoGroups(reports: AdminLandingPageUnresolvedReport[]): {
    ENTERPRISE: AdminLandingPageUnresolvedEnterpriseReport[],
    SERVICE: AdminLandingPageUnresolvedServiceReport[],
    REVIEW: AdminLandingPageUnresolvedReviewReport[],
} {

    const out: {
        ENTERPRISE: AdminLandingPageUnresolvedEnterpriseReport[],
        SERVICE: AdminLandingPageUnresolvedServiceReport[],
        REVIEW: AdminLandingPageUnresolvedReviewReport[],
    } = {
        ENTERPRISE: [],
        SERVICE: [],
        REVIEW: [],
    }

    for (const report of reports) {
        switch (report.subjectType) {
            case "ENTERPRISE":
                out.ENTERPRISE.push(report);
                break;
            case "SERVICE":
                out.SERVICE.push(report);
                break;
            case "REVIEW":
                out.REVIEW.push(report);
                break;
        }
    }
    return out;
}