import { Flex, Heading, Text } from "@chakra-ui/react";
import { DateTimeFormatter } from "@js-joda/core";
import { useNavigate } from "react-router";
import { PayForAppointmentButton } from "../../../common/payment/PayForAppointmentButton";
import { ScrollableList } from "../../../common/ScrollableList";
import { StandardButton } from "../../../common/StandardButton";
import { StandardConcaveBox } from "../../../common/StandardConcaveBox";
import { StandardFlex } from "../../../common/StandardFlex";
import { StandardLabeledContainer } from "../../../common/StandardLabeledContainer";
import { StandardPanel } from "../../../common/StandardPanel";
import { useContextOrThrow } from "../../../hooks/useContextOrThrow";
import { routes } from "../../../router/routes";
import { CustomerLandingPageContext } from "./CustomerLandingPageContext";
import { CustomerLandingPageScheduledAppointment } from "./CustomerLandingPageScheduledAppointment";

export const CustomerLandingPagePastScheduledAppointmentsList = () => {
    const { pastScheduledAppointments } = useContextOrThrow(CustomerLandingPageContext);

    return <StandardLabeledContainer
        label="Past appointments"
        height="100%">
        <StandardConcaveBox maxHeight="100%" minHeight={0}>
            <ScrollableList height="100%">
                {pastScheduledAppointments === null
                    ? null
                    : pastScheduledAppointments.map(Item)}
            </ScrollableList>
        </StandardConcaveBox>
    </StandardLabeledContainer>
}

const Item = (data: CustomerLandingPageScheduledAppointment) => {
    const navigate = useNavigate();

    const date = data.startDatetimeServiceLocal.toLocalDate();
    const dateFormatted = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

    const startFormatted = data.startDatetimeServiceLocal.format(DateTimeFormatter.ofPattern("HH:mm"));
    const endFormatted = data.endDatetimeServiceLocal.format(DateTimeFormatter.ofPattern("HH:mm"));

    const onWriteReviewClick = () => {
        navigate(routes.createServiceReview(data.serviceId));
    }

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
            <Text>
                {data.price} {data.currencyIso}
            </Text>
            <Flex direction="row" gap="5px">
                <StandardButton
                    backgroundColor="primary.blue"
                    onClick={onWriteReviewClick}
                    flex="1">
                    Write a review
                </StandardButton>
                <PayForAppointmentButton
                    isAppointmentPaid={data.isPaid}
                    appointmentId={data.appointmentId}
                    flex="1" />
            </Flex>
        </StandardFlex>
    </StandardPanel>;
}