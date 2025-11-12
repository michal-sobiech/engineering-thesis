import { ScrollableList } from "../../../../common/ScrollableList"
import { StandardConcaveBox } from "../../../../common/StandardConcaveBox"
import { StandardLabeledContainer } from "../../../../common/StandardLabeledContainer"
import { useContextOrThrow } from "../../../../hooks/useContextOrThrow"
import { AdminLandingPageContext } from "./AdminLandingPageContext"
import { AdminLandingPageUnresolvedReviewReportsListItem } from "./AdminLandingPageUnresolvedReviewReportsListItem"

export const AdminLandingPageUnresolvedReviewReportsList = () => {
    const { unresolvedReviewReports } = useContextOrThrow(AdminLandingPageContext);

    return <StandardLabeledContainer label="Review reports">
        <StandardConcaveBox>
            <ScrollableList>
                {unresolvedReviewReports.map(data => <AdminLandingPageUnresolvedReviewReportsListItem {...data} />)}
            </ScrollableList>
        </StandardConcaveBox>
    </StandardLabeledContainer>
}