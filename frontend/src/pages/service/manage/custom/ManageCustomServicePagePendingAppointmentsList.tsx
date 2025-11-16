import { Text } from "@chakra-ui/react";
import { DateTimeFormatter } from "@js-joda/core";
import { FC, useState } from "react";
import { useAppointmentsApi } from "../../../../api/appointments-api";
import { ScrollableList } from "../../../../common/ScrollableList";
import { StandardButton } from "../../../../common/StandardButton";
import { StandardConcaveBox } from "../../../../common/StandardConcaveBox";
import { StandardFlex } from "../../../../common/StandardFlex";
import { StandardLabeledContainer } from "../../../../common/StandardLabeledContainer";
import { StandardPanel } from "../../../../common/StandardPanel";
import { StandardTextArea } from "../../../../common/StandardTextArea";
import { useContextOrThrow } from "../../../../hooks/useContextOrThrow";
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../../../../utils/error";
import { errorErrResultAsyncFromPromise } from "../../../../utils/result";
import { toastError } from "../../../../utils/toast";
import { ManageCustomServicePageContext } from "./ManageCustomServicePageContext";
import { ManageCustomServicePageFutureScheduledAppointment } from "./ManageCustomServicePageFutureScheduledAppointment";

export const ManageCustomServicePagePendingAppointmentsList = () => {
    const appointmentsApi = useAppointmentsApi();

    const { futureScheduledAppointments } = useContextOrThrow(ManageCustomServicePageContext);

    return <StandardLabeledContainer
        label="Pending appointments"
        height="100%">
        <StandardConcaveBox maxHeight="100%" minHeight={5}>
            <ScrollableList height="100%">
                {futureScheduledAppointments === null
                    ? null
                    : futureScheduledAppointments.map(appointment => <Item data={appointment} />)}
            </ScrollableList>
        </StandardConcaveBox>
    </StandardLabeledContainer>
}

interface ItemProps {
    data: ManageCustomServicePageFutureScheduledAppointment,
}

const Item: FC<ItemProps> = ({ data }) => {
    const appointmentsApi = useAppointmentsApi();
    const [rejectionMessage, setRejectionMessage] = useState<string>("");

    const date = data.startDatetimeServiceLocal.toLocalDate();
    const dateFormatted = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

    const startFormatted = data.startDatetimeServiceLocal.format(DateTimeFormatter.ofPattern("HH:mm"));
    const endFormatted = data.endDatetimeServiceLocal.format(DateTimeFormatter.ofPattern("HH:mm"));

    const onAcceptClick = async () => {
        const result = await errorErrResultAsyncFromPromise(appointmentsApi.acceptPendingAppointment(data.appointmentId))
        if (result.isErr()) {
            toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
        } else {
            window.location.reload();
        }
    }

    const onRejectClick = async () => {
        if (rejectionMessage === "") {
            toastError("Enter a reason for rejection");
            return;
        }
        const result = await errorErrResultAsyncFromPromise(appointmentsApi.rejectPendingAppointment(data.appointmentId, { rejectionMessage }));
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
            <StandardButton backgroundColor="primary.lightGreen" onClick={onAcceptClick}>
                Accept
            </StandardButton>
            <StandardTextArea text={rejectionMessage} setText={setRejectionMessage} />
            <StandardButton backgroundColor="primary.darkRed" onClick={onRejectClick}>
                Reject with message
            </StandardButton>
        </StandardFlex>
    </StandardPanel>;
}