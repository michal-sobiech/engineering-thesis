import { ScrollableList } from "../../../common/ScrollableList"
import { StandardConcaveBox } from "../../../common/StandardConcaveBox"
import { StandardLabeledContainer } from "../../../common/StandardLabeledContainer"
import { useContextOrThrow } from "../../../hooks/useContextOrThrow"
import { AdminReportsPageContext } from "./AdminReportsPageContext"
import { AdminReportsPageUnresolvedEnterpriseReportsListItem } from "./AdminReportsPageUnresolvedEnterpriseReportsListItem"

export const AdminReportsPageUnresolvedEnterpriseReportsList = () => {
    const { unresolvedEnterpriseReports } = useContextOrThrow(AdminReportsPageContext);

    return <StandardLabeledContainer label="Enterprise reports">
        <StandardConcaveBox height="100%">
            <ScrollableList height="100%">
                {unresolvedEnterpriseReports.map(data => <AdminReportsPageUnresolvedEnterpriseReportsListItem {...data} />)}
            </ScrollableList>
        </StandardConcaveBox>
    </StandardLabeledContainer>
}