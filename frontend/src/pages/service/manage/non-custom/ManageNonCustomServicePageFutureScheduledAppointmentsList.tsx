import { Text } from "@chakra-ui/react";
import { DateTimeFormatter } from "@js-joda/core";
import { JSX } from "react";
import { useAppointmentsApi } from "../../../../api/appointments-api";
import { ScrollableList } from "../../../../common/ScrollableList";
import { StandardButton } from "../../../../common/StandardButton";
import { StandardConcaveBox } from "../../../../common/StandardConcaveBox";
import { StandardFlex } from "../../../../common/StandardFlex";
import { StandardLabeledContainer } from "../../../../common/StandardLabeledContainer";
import { StandardPanel } from "../../../../common/StandardPanel";
import { AppointmentsApi } from "../../../../GENERATED-api";
import { useContextOrThrow } from "../../../../hooks/useContextOrThrow";
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../../../../utils/error";
import { errorErrResultAsyncFromPromise } from "../../../../utils/result";
import { toastError } from "../../../../utils/toast";
import { ManageNonCustomServicePageContext } from "./ManageNonCustomServicePageContext";
import { ManageNonCustomServicePageFutureScheduledAppointment } from "./ManageNonCustomServicePageFutureScheduledAppointment";

export const ManageNonCustomServicePageFutureScheduledAppointmentsList = () => {
    const appointmentsApi = useAppointmentsApi();

    const { futureScheduledAppointments } = useContextOrThrow(ManageNonCustomServicePageContext);

    return <StandardLabeledContainer
        label="Upcoming scheduled appointments"
        height="100%">
        <StandardConcaveBox maxHeight="100%" minHeight={0}>
            <ScrollableList height="100%">
                {futureScheduledAppointments === null
                    ? null
                    : futureScheduledAppointments.map(appointment => createItem(appointment, appointmentsApi))}
            </ScrollableList>
        </StandardConcaveBox>
    </StandardLabeledContainer>
}

function createItem(data: ManageNonCustomServicePageFutureScheduledAppointment, appointmentsApi: AppointmentsApi): JSX.Element {
    const date = data.startDatetimeServiceLocal.toLocalDate();
    const dateFormatted = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

    const startFormatted = data.startDatetimeServiceLocal.format(DateTimeFormatter.ofPattern("HH:mm"));
    const endFormatted = data.endDatetimeServiceLocal.format(DateTimeFormatter.ofPattern("HH:mm"));

    const onCancelClick = async () => {
        const result = await errorErrResultAsyncFromPromise(appointmentsApi.cancelAppointment(data.appointmentId));
        if (result.isErr()) {
            toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
        } else {
            window.location.reload();
        }
    }

    return <StandardPanel>
        <StandardFlex>
            <Text>
                Username: {data.username}
            </Text>
            <Text>
                {data.userFirstName} {data.userLastName}
            </Text>
            <Text>
                {"\u{1F4CD}"} {data.address}
            </Text>
            <Text>
                {dateFormatted} {startFormatted} - {endFormatted}
            </Text>
            <Text>
                {data.price} {data.currency}
            </Text>
            <StandardButton backgroundColor="primary.darkRed" onClick={onCancelClick}>
                Cancel
            </StandardButton>
        </StandardFlex>
    </StandardPanel>;
}