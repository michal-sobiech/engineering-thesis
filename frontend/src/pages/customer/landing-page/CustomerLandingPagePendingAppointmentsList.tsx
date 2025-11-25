import { Heading, Text } from "@chakra-ui/react";
import { DateTimeFormatter } from "@js-joda/core";
import { useAppointmentsApi } from "../../../api/appointments-api";
import { ScrollableList } from "../../../common/ScrollableList";
import { StandardButton } from "../../../common/StandardButton";
import { StandardConcaveBox } from "../../../common/StandardConcaveBox";
import { StandardFlex } from "../../../common/StandardFlex";
import { StandardLabeledContainer } from "../../../common/StandardLabeledContainer";
import { StandardPanel } from "../../../common/StandardPanel";
import { useContextOrThrow } from "../../../hooks/useContextOrThrow";
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../../../utils/error";
import { refresh } from "../../../utils/refresh";
import { errorErrResultAsyncFromPromise } from "../../../utils/result";
import { toastError } from "../../../utils/toast";
import { CustomerLandingPageContext } from "./CustomerLandingPageContext";
import { CustomerLandingPagePendingAppointment } from "./CustomerLandingPagePendingAppointment";

export const CustomerLandingPagePendingAppointmentsList = () => {
    const { pendingAppointments } = useContextOrThrow(CustomerLandingPageContext);

    return <StandardLabeledContainer
        label="Pending appointments"
        height="100%">
        <StandardConcaveBox maxHeight="100%" minHeight={0}>
            <ScrollableList height="100%">
                {pendingAppointments === null
                    ? null
                    : pendingAppointments.map(Item)}
            </ScrollableList>
        </StandardConcaveBox>
    </StandardLabeledContainer>
}

const Item = (data: CustomerLandingPagePendingAppointment) => {
    const appointmentsApi = useAppointmentsApi();

    const date = data.startDatetimeServiceLocal.toLocalDate();
    const dateFormatted = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

    const startFormatted = data.startDatetimeServiceLocal.format(DateTimeFormatter.ofPattern("HH:mm"));
    const endFormatted = data.endDatetimeServiceLocal.format(DateTimeFormatter.ofPattern("HH:mm"));

    const onClick = async () => {
        const promise = appointmentsApi.cancelAppointment(data.appointmentId);
        const resultAsync = errorErrResultAsyncFromPromise(promise);
        const result = await resultAsync;
        if (result.isErr()) {
            toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
        } else {
            refresh();
        }
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
            <StandardButton backgroundColor="primary.darkRed" onClick={onClick}>
                Withdraw appointment proposal
            </StandardButton>
        </StandardFlex>
    </StandardPanel>;
}