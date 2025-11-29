import { FC } from "react";
import { useAppointmentsApi } from "../../api/appointments-api";
import { StandardButton } from "../StandardButton";

export interface PayForAppointmentButtonProps {
    appointmentId: number;
}

export const PayForAppointmentButton: FC<PayForAppointmentButtonProps> = ({ appointmentId }) => {
    const appointmentsApi = useAppointmentsApi();

    // const onClick = async () => {
    //     const promise = appointmentsApi.payForAppointment(appointmentId);
    //     const resultAsync = errorErrResultAsyncFromPromise(promise);
    //     const result = await resultAsync;

    //     if (result.isErr()) {
    //         toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
    //         return;
    //     }

    //     window.location.href = result.value.paymentUrl;
    // }

    return <StandardButton>
        Pay for appointment
    </StandardButton>;
}