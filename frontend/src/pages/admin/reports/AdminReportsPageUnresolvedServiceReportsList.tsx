import { ScrollableList } from "../../../common/ScrollableList"
import { StandardConcaveBox } from "../../../common/StandardConcaveBox"
import { StandardLabeledContainer } from "../../../common/StandardLabeledContainer"
import { useContextOrThrow } from "../../../hooks/useContextOrThrow"
import { AdminReportsPageContext } from "./AdminReportsPageContext"
import { AdminReportsPageUnresolvedServiceReportsListItem } from "./AdminReportsPageUnresolvedServiceReportsListItem"

export const AdminReportsPageUnresolvedServiceReportsList = () => {
    const { unresolvedServiceReports } = useContextOrThrow(AdminReportsPageContext);

    return <StandardLabeledContainer label="Service reports">
        <StandardConcaveBox>
            <ScrollableList>
                {unresolvedServiceReports.map(data => <AdminReportsPageUnresolvedServiceReportsListItem {...data} />)}
            </ScrollableList>
        </StandardConcaveBox>
    </StandardLabeledContainer>
}