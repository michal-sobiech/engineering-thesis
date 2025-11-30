import { FC } from "react";
import { useAppointmentsApi } from "../../api/appointments-api";
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../../utils/error";
import { errorErrResultAsyncFromPromise } from "../../utils/result";
import { toastError } from "../../utils/toast";
import { StandardButton } from "../StandardButton";

export interface PayForAppointmentButtonProps {
    isAppointmentPaid: boolean;
    appointmentId: number;
}

export const PayForAppointmentButton: FC<PayForAppointmentButtonProps> = ({ isAppointmentPaid, appointmentId }) => {
    return isAppointmentPaid
        ? <PayForAppointmentButtonAppointmentPaid />
        : <PayForAppointmentButtonAppointmentNotPaid appointmentId={appointmentId} />;
};

const PayForAppointmentButtonAppointmentNotPaid = ({ appointmentId }: { appointmentId: number }) => {
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

    return <StandardButton onClick={onClick}>
        Pay
    </StandardButton>;
};

const PayForAppointmentButtonAppointmentPaid = () => {
    return <StandardButton disabled>
        Already paid
    </StandardButton>
};