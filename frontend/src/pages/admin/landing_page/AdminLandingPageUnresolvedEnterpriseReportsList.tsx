import { ScrollableList } from "../../../common/ScrollableList"
import { StandardConcaveBox } from "../../../common/StandardConcaveBox"
import { StandardLabeledContainer } from "../../../common/StandardLabeledContainer"

export const AdminLandingPageUnresolvedEnterpriseReportsList = () => {
    const [unresolvedEnterpriseReports,]

    return <StandardLabeledContainer label="Upcoming scheduled appointments">
        <StandardConcaveBox>
            <ScrollableList>
                {futureScheduledAppointments === null
                    ? null
                    : futureScheduledAppointments.map(createItem)}
            </ScrollableList>
        </StandardConcaveBox>
    </StandardLabeledContainer>
}