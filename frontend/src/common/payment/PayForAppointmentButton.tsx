import { ButtonProps } from "@chakra-ui/react";
import { FC } from "react";
import { useAppointmentsApi } from "../../api/appointments-api";
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../../utils/error";
import { errorErrResultAsyncFromPromise } from "../../utils/result";
import { toastError } from "../../utils/toast";
import { StandardButton } from "../StandardButton";

export type PayForAppointmentButtonProps = ButtonProps & {
    isAppointmentPaid: boolean;
    appointmentId: number;
}

export const PayForAppointmentButton: FC<PayForAppointmentButtonProps> = ({ isAppointmentPaid, appointmentId, ...props }) => {
    return isAppointmentPaid
        ? <PayForAppointmentButtonAppointmentPaid {...props} />
        : <PayForAppointmentButtonAppointmentNotPaid appointmentId={appointmentId} {...props} />;
};

const PayForAppointmentButtonAppointmentNotPaid: FC<ButtonProps & { appointmentId: number }> = ({ appointmentId, ...props }) => {
    const appointmentsApi = useAppointmentsApi();

    const onClick = async () => {
        const promise = appointmentsApi.createAdyenSession(appointmentId);
        const resultAsync = errorErrResultAsyncFromPromise(promise);
        const result = await resultAsync;

        if (result.isErr()) {
            toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
            return;
        }

        window.location.href = result.value.redirectUrl;
    }

    return <StandardButton {...props} onClick={onClick}>
        Pay
    </StandardButton>;
};

const PayForAppointmentButtonAppointmentPaid: FC<ButtonProps> = (props) => {
    return <StandardButton {...props} disabled>
        Already paid
    </StandardButton>
};