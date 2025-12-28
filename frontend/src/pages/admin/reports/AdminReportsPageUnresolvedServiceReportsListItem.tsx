import { Heading, Text } from "@chakra-ui/react";
import { FC } from "react";
import { useReportsApi } from "../../../api/reports-api";
import { LinkText } from "../../../common/LinkText";
import { StandardFlex } from "../../../common/StandardFlex";
import { StandardLabeledContainer } from "../../../common/StandardLabeledContainer";
import { StandardPanel } from "../../../common/StandardPanel";
import { StandardTextArea } from "../../../common/StandardTextArea";
import { useContextOrThrow } from "../../../hooks/useContextOrThrow";
import { routes } from "../../../router/routes";
import { AdminReportsPageContext } from "./AdminReportsPageContext";
import { AdminReportsPageUnresolvedServiceReport } from "./AdminReportsPageUnresolvedReport";

export const AdminReportsPageUnresolvedServiceReportsListItem: FC<AdminReportsPageUnresolvedServiceReport> = (props) => {
    const reportsApi = useReportsApi();

    const { setUnresolvedServiceReports } = useContextOrThrow(AdminReportsPageContext);

    function removeUnresolvedReportFromList(reportId: number) {
        setUnresolvedServiceReports((reports: AdminReportsPageUnresolvedServiceReport[]) => {
            return reports.filter(report => report.reportId != reportId)
        });
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
            <LinkText url={routes.servicePublicPage(props.serviceId)}>
                Name: {props.serviceName}
            </LinkText>

            <StandardLabeledContainer label="Reason for report">
                <StandardTextArea disabled text={props.content} setText={() => { }} />
            </StandardLabeledContainer>
        </StandardFlex>
    </StandardPanel>;
}