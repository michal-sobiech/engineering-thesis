import { ScrollableList } from "../../../common/ScrollableList"
import { StandardConcaveBox } from "../../../common/StandardConcaveBox"
import { StandardLabeledContainer } from "../../../common/StandardLabeledContainer"
import { useContextOrThrow } from "../../../utils/useContextOrThrow"
import { AdminLandingPageContext } from "./AdminLandingPageContext"
import { AdminLandingPageUnresolvedEnterpriseReportsListItem } from "./AdminLandingPageUnresolvedEnterpriseReportsListItem"

export const AdminLandingPageUnresolvedEnterpriseReportsList = () => {
    const { unresolvedEnterpriseReports } = useContextOrThrow(AdminLandingPageContext);

    return <StandardLabeledContainer label="Enterprise reports">
        <StandardConcaveBox>
            <ScrollableList>
                {unresolvedEnterpriseReports.map(data => <AdminLandingPageUnresolvedEnterpriseReportsListItem {...data} />)}
            </ScrollableList>
        </StandardConcaveBox>
    </StandardLabeledContainer>
}