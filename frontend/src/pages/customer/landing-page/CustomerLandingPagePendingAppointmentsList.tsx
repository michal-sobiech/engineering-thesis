import { ScrollableList } from "../../../common/ScrollableList";
import { StandardConcaveBox } from "../../../common/StandardConcaveBox";
import { StandardLabeledContainer } from "../../../common/StandardLabeledContainer";

export const CustomerLandingPagePendingAppointmentsList = () => {
    return <StandardLabeledContainer label="Pending appointment proposals">
        <StandardConcaveBox>
            <ScrollableList>
            </ScrollableList>
        </StandardConcaveBox>
    </StandardLabeledContainer>;
}

// function createItem(data: CustomerLandingPageScheduledAppointment): JSX.Element {
//     const date = data.startServiceLocal.toLocalDate();
//     const dateFormatted = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

//     const startFormatted = data.startServiceLocal.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
//     const endFormatted = data.endServiceLocal.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));

//     return <StandardPanel>
//         <StandardFlex>
//             <Heading>
//                 {data.serviceName}
//             </Heading>
//             <Text>
//                 {"\u{1F4CD}"} {data.address}
//             </Text>
//             <Text>
//                 {dateFormatted} {startFormatted} - {endFormatted}
//             </Text>
//             <StandardButton backgroundColor="primary.darkRed">
//                 Cancel
//             </StandardButton>
//         </StandardFlex>
//     </StandardPanel>;
// }