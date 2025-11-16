import { ScrollableList } from "../../../common/ScrollableList"
import { StandardConcaveBox } from "../../../common/StandardConcaveBox"
import { StandardLabeledContainer } from "../../../common/StandardLabeledContainer"
import { useContextOrThrow } from "../../../hooks/useContextOrThrow"
import { AdminReportsPageContext } from "./AdminReportsPageContext"
import { AdminReportsPageUnresolvedReviewReportsListItem } from "./AdminReportsPageUnresolvedReviewReportsListItem"

export const AdminReportsPageUnresolvedReviewReportsList = () => {
    const { unresolvedReviewReports } = useContextOrThrow(AdminReportsPageContext);

    return <StandardLabeledContainer label="Review reports">
        <StandardConcaveBox>
            <ScrollableList>
                {unresolvedReviewReports.map(data => <AdminReportsPageUnresolvedReviewReportsListItem {...data} />)}
            </ScrollableList>
        </StandardConcaveBox>
    </StandardLabeledContainer>
}