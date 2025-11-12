import { LocalDateTime } from "@js-joda/core";
import { useAppointmentsApi } from "../../../../api/appointments-api";
import { StandardButton } from "../../../../common/StandardButton";
import { useContextOrThrow } from "../../../../hooks/useContextOrThrow";
import { useIntParam } from "../../../../hooks/useIntParam";
import { useNavigateWithToastDismiss } from "../../../../hooks/useNavigateWithToastDismiss";
import { routes } from "../../../../router/routes";
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../../../../utils/error";
import { errorErrResultAsyncFromPromise } from "../../../../utils/result";
import { toastError } from "../../../../utils/toast";
import { NoCustomAppointmentsServicePublicPageContext } from "./NoCustomAppointmentsServicePublicPageContextValue";

export const NonCustomAppointmentsServicePublicPageAppointmentMakerButton = () => {
    const appointmentsApi = useAppointmentsApi();
    const serviceId = useIntParam("serviceId");

    const {
        selectedDate,
        selectedSlot,
    } = useContextOrThrow(NoCustomAppointmentsServicePublicPageContext);

    const navigate = useNavigateWithToastDismiss();

    const onClick = async () => {
        if (selectedDate === null) {
            toastError("Select a date");
            return;
        }
        if (selectedSlot === null) {
            toastError("Select a slot");
            return;
        }

        const startServiceLocal = LocalDateTime.of(selectedDate, selectedSlot[0]);
        const endServiceLocal = LocalDateTime.of(selectedDate, selectedSlot[0]);

        const promise = appointmentsApi.createNonCustomAppointment(serviceId, {
            startDatetimeShopLocal: startServiceLocal.toString(),
            endDatetimeShopLocal: endServiceLocal.toString(),
        });
        const result = await errorErrResultAsyncFromPromise(promise);

        if (result.isErr()) {
            toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
            return;
        }

        navigate(routes.mainPage);
    }

    const enabled = selectedDate !== null && selectedSlot !== null;

    return <StandardButton onClick={onClick} disabled={!enabled}>Book an appointment</StandardButton>;
}