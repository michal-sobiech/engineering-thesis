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
import { CustomAppointmentsServicePublicPageContext } from "./CustomAppointmentsServicePublicPageContextValue";

export const CustomAppointmentsServicePublicPageAppointmentMakerButton = () => {
    const appointmentsApi = useAppointmentsApi();

    const {
        selectedDate,
        selectedTimeWindowStart,
        selectedTimeWindowEnd,
        address,
        position,
    } = useContextOrThrow(CustomAppointmentsServicePublicPageContext);

    const serviceId = useIntParam("serviceId");

    const navigate = useNavigateWithToastDismiss();

    const onClick = async () => {
        if (selectedDate === null) {
            toastError("Select a date");
            return;
        }
        if (selectedTimeWindowStart === null) {
            toastError("Select a start time");
            return;
        }
        if (selectedTimeWindowEnd === null) {
            toastError("Select an end time");
            return;
        }
        if (address === null || position === null) {
            toastError("Choose a location");
            return;
        }

        const startServiceLocal = LocalDateTime.of(selectedDate, selectedTimeWindowStart);
        const endServiceLocal = LocalDateTime.of(selectedDate, selectedTimeWindowEnd);

        const promise = appointmentsApi.createCustomAppointment(serviceId, {
            startDatetimeShopLocal: startServiceLocal.toString(),
            endDatetimeShopLocal: endServiceLocal.toString(),
            location: {
                address: address,
                longitude: position.x,
                latitude: position.y,
            }
        });
        const result = await errorErrResultAsyncFromPromise(promise);

        if (result.isErr()) {
            toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
            return;
        }

        navigate(routes.mainPage);
    }

    return <StandardButton onClick={onClick}>Submit appointment proposal</StandardButton>;
}