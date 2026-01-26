import { LocalDate, LocalDateTime } from "@js-joda/core";
import { useNavigate } from "react-router";
import { useAppointmentsApi } from "../../../../api/appointments-api";
import { useServiceIdFromLoader } from "../../../../common/loader/service-id-loader";
import { StandardButton } from "../../../../common/StandardButton";
import { CreateNonCustomAppointmentOperationRequest, ResponseError } from "../../../../GENERATED-api";
import { useContextOrThrow } from "../../../../hooks/useContextOrThrow";
import { routes } from "../../../../router/routes";
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../../../../utils/error";
import { toastError, toastSuccess } from "../../../../utils/toast";
import { NoCustomAppointmentsServicePublicPageContext } from "./NoCustomAppointmentsServicePublicPageContextValue";

export const NonCustomAppointmentsServicePublicPageAppointmentMakerButton = () => {
    const appointmentsApi = useAppointmentsApi();
    const serviceId = useServiceIdFromLoader();

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

        const yesterday = LocalDate.now().minusDays(1);
        if (selectedDate.isBefore(yesterday)) {
            toastError("Date cannot be from the past");
            return;
        }

        const startServiceLocal = LocalDateTime.of(selectedDate, selectedSlot[0]);
        const endServiceLocal = LocalDateTime.of(selectedDate, selectedSlot[1]);

        const requestParameters: CreateNonCustomAppointmentOperationRequest = {
            serviceId,
            createNonCustomAppointmentRequest: {
                startDatetimeEnterpriseServiceLocal: startServiceLocal.toString(),
                endDatetimeEnterpriseServiceLocal: endServiceLocal.toString(),
            },
        };

        try {
            await appointmentsApi.createNonCustomAppointmentRaw(requestParameters);
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