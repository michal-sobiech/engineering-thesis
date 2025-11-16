import { Heading, Text } from "@chakra-ui/react";
import { FC } from "react";
import { useReportsApi } from "../../../api/reports-api";
import { StandardButton } from "../../../common/StandardButton";
import { StandardFlex } from "../../../common/StandardFlex";
import { StandardPanel } from "../../../common/StandardPanel";
import { useContextOrThrow } from "../../../hooks/useContextOrThrow";
import { errorErrResultAsyncFromPromise } from "../../../utils/result";
import { toastError } from "../../../utils/toast";
import { AdminReportsPageContext } from "./AdminReportsPageContext";
import { AdminReportsPageUnresolvedEnterpriseReport } from "./AdminReportsPageUnresolvedReport";

export const AdminReportsPageUnresolvedEnterpriseReportsListItem: FC<AdminReportsPageUnresolvedEnterpriseReport> = (props) => {
    const reportsApi = useReportsApi();

    const { setUnresolvedEnterpriseReports } = useContextOrThrow(AdminReportsPageContext);

    function removeUnresolvedEnterpriseReportFromList(reportId: number) {
        setUnresolvedEnterpriseReports((reports: AdminReportsPageUnresolvedEnterpriseReport[]) => reports.filter(report => report.reportId != reportId));
    }

    const onClick = async () => {
        const promise = reportsApi.resolveReport(props.reportId);
        const result = await errorErrResultAsyncFromPromise(promise);
        if (result.isErr()) {
            toastError("Couldn't resolve report. Try again later");
            return;
        }
        removeUnresolvedEnterpriseReportFromList(props.reportId);
    }

    return <StandardPanel>
        <StandardFlex>
            <Heading>
                Submitted by
            </Heading>
            <Text>
                User ID: {props.reportingUserId}
            </Text>
            <Text>
                User type: {props.reportingUserType}
            </Text>
            <Text>
                Username: {props.reportingUserUsername}
            </Text>

            <Heading>
                Subject of report
            </Heading>
            <Text>
                Type: Enterprise
            </Text>
            <Text>
                Enterprise ID: {props.enterpriseId}
            </Text>
            <Text>
                Name: {props.enterpriseName}
            </Text>

            <StandardButton backgroundColor="primary.darkRed" onClick={onClick}>
                Suspend
            </StandardButton>
        </StandardFlex>
    </StandardPanel>;
}