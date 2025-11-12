import { Heading, Text } from "@chakra-ui/react";
import { FC } from "react";
import { useReportsApi } from "../../../../api/reports-api";
import { StandardButton } from "../../../../common/StandardButton";
import { StandardFlex } from "../../../../common/StandardFlex";
import { StandardPanel } from "../../../../common/StandardPanel";
import { useContextOrThrow } from "../../../../hooks/useContextOrThrow";
import { errorErrResultAsyncFromPromise } from "../../../../utils/result";
import { toastError } from "../../../../utils/toast";
import { AdminLandingPageContext } from "./AdminLandingPageContext";
import { AdminLandingPageUnresolvedServiceReport } from "./AdminLandingPageUnresolvedReport";

export const AdminLandingPageUnresolvedServiceReportsListItem: FC<AdminLandingPageUnresolvedServiceReport> = (props) => {
    const reportsApi = useReportsApi();

    const { setUnresolvedServiceReports } = useContextOrThrow(AdminLandingPageContext);

    function removeUnresolvedReportFromList(reportId: number) {
        setUnresolvedServiceReports((reports: AdminLandingPageUnresolvedServiceReport[]) => {
            return reports.filter(report => report.reportId != reportId)
        });
    }

    const onClick = async () => {
        const promise = reportsApi.resolveReport(props.reportId);
        const result = await errorErrResultAsyncFromPromise(promise);
        if (result.isErr()) {
            toastError("Couldn't resolve report. Try again later");
            return;
        }
        removeUnresolvedReportFromList(props.reportId);
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
                Type: service
            </Text>
            <Text>
                Service ID: {props.serviceId}
            </Text>
            <Text>
                Name: {props.serviceName}
            </Text>

            <StandardButton backgroundColor="primary.darkRed" onClick={onClick}>
                Suspend
            </StandardButton>
        </StandardFlex>
    </StandardPanel>;
}