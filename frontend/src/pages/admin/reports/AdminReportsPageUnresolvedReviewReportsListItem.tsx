import { Heading, Text } from "@chakra-ui/react";
import { FC } from "react";
import { useReportsApi } from "../../../api/reports-api";
import { StandardFlex } from "../../../common/StandardFlex";
import { StandardLabeledContainer } from "../../../common/StandardLabeledContainer";
import { StandardPanel } from "../../../common/StandardPanel";
import { StandardTextArea } from "../../../common/StandardTextArea";
import { useContextOrThrow } from "../../../hooks/useContextOrThrow";
import { AdminReportsPageContext } from "./AdminReportsPageContext";
import { AdminReportsPageUnresolvedReviewReport } from "./AdminReportsPageUnresolvedReport";

export const AdminReportsPageUnresolvedReviewReportsListItem: FC<AdminReportsPageUnresolvedReviewReport> = (props) => {
    const reportsApi = useReportsApi();

    const { setUnresolvedReviewReports } = useContextOrThrow(AdminReportsPageContext);

    function removeUnresolvedReviewReportFromList(reportId: number) {
        setUnresolvedReviewReports((reports: AdminReportsPageUnresolvedReviewReport[]) => {
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
                Type: review
            </Text>
            <Text>
                Review ID: {props.reviewId}
            </Text>
            <Text>
                Text: {props.reviewText}
            </Text>

            <StandardLabeledContainer label="Reason for report">
                <StandardTextArea disabled text={props.content} setText={() => { }} />
            </StandardLabeledContainer>
        </StandardFlex>
    </StandardPanel>;
}