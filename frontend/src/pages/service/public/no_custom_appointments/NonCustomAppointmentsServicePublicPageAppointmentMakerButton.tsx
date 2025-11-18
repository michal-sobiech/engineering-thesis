import { LocalDateTime } from "@js-joda/core";
import { useNavigate } from "react-router";
import { useAppointmentsApi } from "../../../../api/appointments-api";
import { StandardButton } from "../../../../common/StandardButton";
import { ResponseError } from "../../../../GENERATED-api";
import { useContextOrThrow } from "../../../../hooks/useContextOrThrow";
import { useIntParam } from "../../../../hooks/useIntParam";
import { routes } from "../../../../router/routes";
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../../../../utils/error";
import { toastError, toastSuccess } from "../../../../utils/toast";
import { NoCustomAppointmentsServicePublicPageContext } from "./NoCustomAppointmentsServicePublicPageContextValue";

export const NonCustomAppointmentsServicePublicPageAppointmentMakerButton = () => {
    const appointmentsApi = useAppointmentsApi();
    const serviceId = useIntParam("serviceId");

    const {
        selectedDate,
        selectedSlot,
    } = useContextOrThrow(NoCustomAppointmentsServicePublicPageContext);

    const navigate = useNavigate();

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

        const requestParameters = {
            serviceId,
            createNonCustomAppointmentRequest: {
                startDatetimeShopLocal: startServiceLocal.toString(),
                endDatetimeShopLocal: endServiceLocal.toString(),
            },
        };

        // TODO fix other fetch code relying on statuses

        try {
            const promise = appointmentsApi.createNonCustomAppointmentRaw(requestParameters);
            await promise;
            toastSuccess("Booked an appointment!")
            navigate(routes.customerLandingPage);
        } catch (error: unknown) {
            if (error instanceof ResponseError) {
                if (error.response.status === 401) {
                    toastError("Log in as customer first!");
                } else {
                    toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
                }
            } else {
                toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
            }
        }
    }

    const enabled = selectedDate !== null && selectedSlot !== null;

    return <StandardButton onClick={onClick} disabled={!enabled}>Book an appointment</StandardButton>;
}