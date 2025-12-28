import { Center, Flex } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import { useReportsApi } from "../../../api/reports-api";
import { StandardPanel } from "../../../common/StandardPanel";
import { GetUnresolvedReports200ResponseInner } from "../../../GENERATED-api";
import { routes } from "../../../router/routes";
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../../../utils/error";
import { errorErrResultAsyncFromPromise } from "../../../utils/result";
import { toastError } from "../../../utils/toast";
import { AdminReportsPageContext, AdminReportsPageContextValue } from "./AdminReportsPageContext";
import { AdminReportsPageUnresolvedEnterpriseReportsList } from "./AdminReportsPageUnresolvedEnterpriseReportsList";
import { AdminReportsPageUnresolvedEnterpriseReport, AdminReportsPageUnresolvedReport, AdminReportsPageUnresolvedReviewReport, AdminReportsPageUnresolvedServiceReport } from "./AdminReportsPageUnresolvedReport";
import { AdminReportsPageUnresolvedReviewReportsList } from "./AdminReportsPageUnresolvedReviewReportsList";
import { AdminReportsPageUnresolvedServiceReportsList } from "./AdminReportsPageUnresolvedServiceReportsList";

export const AdminReportsPage = () => {
    const [unresolvedEnterpriseReports, setUnresolvedEnterpriseReports] = useState<AdminReportsPageUnresolvedEnterpriseReport[]>([]);
    const [unresolvedServiceReports, setUnresolvedServiceReports] = useState<AdminReportsPageUnresolvedServiceReport[]>([]);
    const [unresolvedReviewReports, setUnresolvedReviewReports] = useState<AdminReportsPageUnresolvedReviewReport[]>([]);

    const navigate = useNavigate();
    const reportsApi = useReportsApi();

    const contextValue: AdminReportsPageContextValue = {
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

    return <AdminReportsPageContext.Provider value={contextValue}>
        <Center height="100%">
            <StandardPanel height="100%" width="80%">
                <Flex height="100%" direction="row" gap="10px">
                    {unresolvedEnterpriseReports.length !== 0
                        ? <AdminReportsPageUnresolvedEnterpriseReportsList />
                        : null}
                    {unresolvedServiceReports.length !== 0
                        ? <AdminReportsPageUnresolvedServiceReportsList />
                        : null}
                    {unresolvedReviewReports.length !== 0
                        ? <AdminReportsPageUnresolvedReviewReportsList />
                        : null}
                </Flex>
            </StandardPanel>
        </Center>
    </AdminReportsPageContext.Provider>
}

function createReportDomain(reportDto: GetUnresolvedReports200ResponseInner): AdminReportsPageUnresolvedReport {
    if ("enterpriseId" in reportDto) {
        return {
            reportId: reportDto.reportId,

            content: reportDto.content,

            reportingUserType: reportDto.reportingUserType,
            reportingUserId: reportDto.reportingUserId,
            reportingUserUsername: reportDto.reportingUserUsername,

            subjectType: "ENTERPRISE",
            enterpriseId: reportDto.enterpriseId,
            enterpriseName: reportDto.enterpriseName,
        } satisfies AdminReportsPageUnresolvedEnterpriseReport;
    } else if ("enterpriseServiceId" in reportDto) {
        return {
            reportId: reportDto.reportId,

            content: reportDto.content,

            reportingUserType: reportDto.reportingUserType,
            reportingUserId: reportDto.reportingUserId,
            reportingUserUsername: reportDto.reportingUserUsername,

            subjectType: "SERVICE",
            serviceId: reportDto.enterpriseServiceId,
            serviceName: reportDto.enterpriseServiceName,
        } satisfies AdminReportsPageUnresolvedServiceReport;
    } else if ("reviewId" in reportDto) {
        return {
            reportId: reportDto.reportId,

            content: reportDto.content,

            reportingUserType: reportDto.reportingUserType,
            reportingUserId: reportDto.reportingUserId,
            reportingUserUsername: reportDto.reportingUserUsername,

            subjectType: "REVIEW",
            reviewId: reportDto.reviewId,
            reviewText: reportDto.reviewText,
        } satisfies AdminReportsPageUnresolvedReviewReport;
    } else {
        throw new Error("Invalid argument, couldn't match with any supported report subject type");
    }
}

function splitReportsIntoGroups(reports: AdminReportsPageUnresolvedReport[]): {
    ENTERPRISE: AdminReportsPageUnresolvedEnterpriseReport[],
    SERVICE: AdminReportsPageUnresolvedServiceReport[],
    REVIEW: AdminReportsPageUnresolvedReviewReport[],
} {

    const out: {
        ENTERPRISE: AdminReportsPageUnresolvedEnterpriseReport[],
        SERVICE: AdminReportsPageUnresolvedServiceReport[],
        REVIEW: AdminReportsPageUnresolvedReviewReport[],
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