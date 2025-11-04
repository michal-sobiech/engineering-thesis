import { StandardButton } from "../../../../common/StandardButton";
import { useNavigateWithToastDismiss } from "../../../../hooks/useNavigateWithToastDismiss";
import { routes } from "../../../../router/routes";
import { toastError } from "../../../../utils/toast";
import { useContextOrThrow } from "../../../../utils/useContextOrThrow";
import { NoCustomAppointmentsServicePublicPageContext } from "./NoCustomAppointmentsServicePublicPageContextValue";

export const NonCustomAppointmentsServicePublicPageAppointmentMakerButton = () => {
    const {
        selectedDate,
        selectedSlot,
    } = useContextOrThrow(NoCustomAppointmentsServicePublicPageContext);

    const navigate = useNavigateWithToastDismiss();

    const onClick = () => {
        if (selectedDate === null) {
            toastError("Select a date");
            return;
        }
        if (selectedSlot === null) {
            toastError("Select a slot");
        }
        // TODO
        navigate(routes.mainPage);
    }

    const enabled = selectedDate !== null && selectedSlot !== null;

    return <StandardButton onClick={onClick} disabled={!enabled}>Book an appointment</StandardButton>;
}