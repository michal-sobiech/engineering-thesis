import { ScrollableList } from "../../../common/ScrollableList"
import { StandardConcaveBox } from "../../../common/StandardConcaveBox"
import { StandardLabeledContainer } from "../../../common/StandardLabeledContainer"
import { useContextOrThrow } from "../../../utils/useContextOrThrow"
import { AdminLandingPageContext } from "./AdminLandingPageContext"
import { AdminLandingPageUnresolvedServiceReportsListItem } from "./AdminLandingPageUnresolvedServiceReportsListItem"

export const AdminLandingPageUnresolvedServiceReportsList = () => {
    const { unresolvedServiceReports } = useContextOrThrow(AdminLandingPageContext);

    return <StandardLabeledContainer label="Service reports">
        <StandardConcaveBox>
            <ScrollableList>
                {unresolvedServiceReports.map(data => <AdminLandingPageUnresolvedServiceReportsListItem {...data} />)}
            </ScrollableList>
        </StandardConcaveBox>
    </StandardLabeledContainer>
}