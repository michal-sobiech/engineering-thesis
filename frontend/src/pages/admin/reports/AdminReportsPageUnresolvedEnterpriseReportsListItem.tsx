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
import { AdminReportsPageUnresolvedEnterpriseReport } from "./AdminReportsPageUnresolvedReport";

export const AdminReportsPageUnresolvedEnterpriseReportsListItem: FC<AdminReportsPageUnresolvedEnterpriseReport> = (props) => {
    const reportsApi = useReportsApi();

    const { setUnresolvedEnterpriseReports } = useContextOrThrow(AdminReportsPageContext);

    function removeUnresolvedEnterpriseReportFromList(reportId: number) {
        setUnresolvedEnterpriseReports((reports: AdminReportsPageUnresolvedEnterpriseReport[]) => reports.filter(report => report.reportId != reportId));
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
            <LinkText url={routes.enterprisePublic(props.enterpriseId)}>
                Name: {props.enterpriseName}
            </LinkText>

            <StandardLabeledContainer label="Reason for report">
                <StandardTextArea disabled text={props.content} setText={() => { }} />
            </StandardLabeledContainer>
        </StandardFlex>
    </StandardPanel>;
}