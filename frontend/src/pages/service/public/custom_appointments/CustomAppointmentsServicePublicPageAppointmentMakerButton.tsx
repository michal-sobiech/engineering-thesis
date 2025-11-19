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

    const navigate = useNavigate();

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
        const startServiceLocal = LocalDateTime.of(selectedDate, selectedTimeWindowStart);
        const endServiceLocal = LocalDateTime.of(selectedDate, selectedTimeWindowEnd);
        if (!endServiceLocal.isAfter(startServiceLocal)) {
            toastError("End time must be after start time");
            return;
        }

        if (address === null || position === null) {
            toastError("Choose a location");
            return;
        }


        const requestParameters = {
            serviceId,
            createCustomAppointmentRequest: {
                startDatetimeShopLocal: startServiceLocal.toString(),
                endDatetimeShopLocal: endServiceLocal.toString(),
                location: {
                    address: address,
                    longitude: position.x,
                    latitude: position.y,
                }
            }
        };

        try {
            const promise = appointmentsApi.createCustomAppointmentRaw(requestParameters);
            await promise;
            toastSuccess("Submitted appointment proposal!")
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

    return <StandardButton onClick={onClick}>Submit appointment proposal</StandardButton>;
}