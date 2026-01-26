import { LocalDate, LocalDateTime, LocalTime } from "@js-joda/core";
import { useNavigate } from "react-router";
import { useAppointmentsApi } from "../../../../api/appointments-api";
import { useServiceIdFromLoader } from "../../../../common/loader/service-id-loader";
import { StandardButton } from "../../../../common/StandardButton";
import { CreateCustomAppointmentOperationRequest } from "../../../../GENERATED-api";
import { useContextOrThrow } from "../../../../hooks/useContextOrThrow";
import { routes } from "../../../../router/routes";
import { doesLocalTimeWindowGroupFullyContainWindow } from "../../../../utils/date";
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../../../../utils/error";
import { toastError, toastSuccess } from "../../../../utils/toast";
import { CustomAppointmentsServicePublicPageContext } from "./CustomAppointmentsServicePublicPageContextValue";

export const CustomAppointmentsServicePublicPageAppointmentMakerButton = () => {
    const appointmentsApi = useAppointmentsApi();

    const {
        selectedDate,
        freeTimeWindowsOnSelectedDate,
        setFreeTimeWindowsOnSelectedDate,
        selectedTimeWindowStart,
        selectedTimeWindowEnd,
        address,
        position,
    } = useContextOrThrow(CustomAppointmentsServicePublicPageContext);

    const serviceId = useServiceIdFromLoader();
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

        const yesterday = LocalDate.now().minusDays(1);
        if (selectedDate.isBefore(yesterday)) {
            toastError("Date cannot be from the past");
            return;
        }

        const requestParameters: CreateCustomAppointmentOperationRequest = {
            serviceId,
            createCustomAppointmentRequest: {
                startDatetimeEnterpriseServiceLocal: startServiceLocal.toString(),
                endDatetimeEnterpriseServiceLocal: endServiceLocal.toString(),
                location: {
                    address: address,
                    longitude: position.longitude,
                    latitude: position.latitude,
                }
            }
        };

        try {
            const proposedWindow: [LocalTime, LocalTime] = [
                selectedTimeWindowStart, selectedTimeWindowEnd
            ];

            console.log(proposedWindow, freeTimeWindowsOnSelectedDate)
            if (!doesLocalTimeWindowGroupFullyContainWindow(freeTimeWindowsOnSelectedDate ?? [], proposedWindow)) {
                toastError("Choose a time window that fits the schedule");
                return;
            }

            const promise = appointmentsApi.createCustomAppointmentRaw(requestParameters);
            await promise;
            toastSuccess("Submitted appointment proposal!")
            navigate(routes.customerLandingPage);
        } catch (error: unknown) {
            toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
        }

    }

    return <StandardButton onClick={onClick}>Submit appointment proposal</StandardButton>;
}