import { StandardButton } from "../../../../common/StandardButton";
import { useNavigateWithToastDismiss } from "../../../../hooks/useNavigateWithToastDismiss";
import { routes } from "../../../../router/routes";
import { toastError } from "../../../../utils/toast";
import { useContextOrThrow } from "../../../../utils/useContextOrThrow";
import { CustomAppointmentsServicePublicPageContext } from "./CustomAppointmentsServicePublicPageContextValue";

export const CustomAppointmentsServicePublicPageAppointmentMakerButton = () => {
    const {
        selectedDate,
        selectedTimeWindowStart,
        selectedTimeWindowEnd,
    } = useContextOrThrow(CustomAppointmentsServicePublicPageContext);

    const navigate = useNavigateWithToastDismiss();

    const onClick = () => {
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
        }

        navigate(routes.mainPage);
    }

    return <StandardButton onClick={onClick}>Submit appointment proposal</StandardButton>;
}