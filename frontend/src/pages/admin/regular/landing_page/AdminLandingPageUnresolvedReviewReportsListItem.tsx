import { Heading, Text } from "@chakra-ui/react";
import { FC } from "react";
import { reportsApi } from "../../../../api/reports-api";
import { StandardButton } from "../../../../common/StandardButton";
import { StandardFlex } from "../../../../common/StandardFlex";
import { StandardPanel } from "../../../../common/StandardPanel";
import { useContextOrThrow } from "../../../../hooks/useContextOrThrow";
import { errorErrResultAsyncFromPromise } from "../../../../utils/result";
import { toastError } from "../../../../utils/toast";
import { AdminLandingPageContext } from "./AdminLandingPageContext";
import { AdminLandingPageUnresolvedReviewReport } from "./AdminLandingPageUnresolvedReport";

export const AdminLandingPageUnresolvedReviewReportsListItem: FC<AdminLandingPageUnresolvedReviewReport> = (props) => {
    const { setUnresolvedReviewReports } = useContextOrThrow(AdminLandingPageContext);

    function removeUnresolvedReviewReportFromList(reportId: number) {
        setUnresolvedReviewReports((reports: AdminLandingPageUnresolvedReviewReport[]) => {
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
        removeUnresolvedReviewReportFromList(props.reportId);
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

            <StandardButton backgroundColor="primary.darkRed" onClick={onClick}>
                Suspend
            </StandardButton>
        </StandardFlex>
    </StandardPanel>;
}