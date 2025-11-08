import { Heading, Text } from "@chakra-ui/react";
import { DateTimeFormatter } from "@js-joda/core";
import { JSX } from "react";
import { ScrollableList } from "../../../common/ScrollableList";
import { StandardButton } from "../../../common/StandardButton";
import { StandardConcaveBox } from "../../../common/StandardConcaveBox";
import { StandardFlex } from "../../../common/StandardFlex";
import { StandardLabeledContainer } from "../../../common/StandardLabeledContainer";
import { StandardPanel } from "../../../common/StandardPanel";
import { useContextOrThrow } from "../../../utils/useContextOrThrow";
import { CustomerLandingPageContext } from "./CustomerLandingPageContext";
import { CustomerLandingPagePendingAppointment } from "./CustomerLandingPagePendingAppointment";

export const CustomerLandingPagePendingAppointmentsList = () => {
    const { pendingAppointments } = useContextOrThrow(CustomerLandingPageContext);

    return <StandardLabeledContainer label="Past appointments">
        <StandardConcaveBox>
            <ScrollableList>
                {pendingAppointments === null
                    ? null
                    : pendingAppointments.map(createItem)}
            </ScrollableList>
        </StandardConcaveBox>
    </StandardLabeledContainer>
}

function createItem(data: CustomerLandingPagePendingAppointment): JSX.Element {
    const date = data.startDatetimeServiceLocal.toLocalDate();
    const dateFormatted = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

    const startFormatted = data.startDatetimeServiceLocal.format(DateTimeFormatter.ofPattern("HH:mm"));
    const endFormatted = data.endDatetimeServiceLocal.format(DateTimeFormatter.ofPattern("HH:mm"));

    return <StandardPanel>
        <StandardFlex>
            <Heading>
                {data.serviceName}
            </Heading>
            <Text>
                {"\u{1F4CD}"} {data.address}
            </Text>
            <Text>
                {dateFormatted} {startFormatted} - {endFormatted}
            </Text>
            <StandardButton backgroundColor="primary.darkRed">
                Withdraw appointment proposal
            </StandardButton>
        </StandardFlex>
    </StandardPanel>;
}