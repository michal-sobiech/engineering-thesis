import { Center } from "@chakra-ui/react";
import { useContextOrThrow } from "../../../../hooks/useContextOrThrow";
import { NoCustomAppointmentsServicePublicPageContext } from "./NoCustomAppointmentsServicePublicPageContextValue";
import { NonCustomAppointmentsServicePublicPageAppointmentMakerButton } from "./NonCustomAppointmentsServicePublicPageAppointmentMakerButton";

export const NonCustomAppointmentsServicePublicPageAppointmentMaker = () => {
    const { freeSlotsOnSelectedDate } = useContextOrThrow(NoCustomAppointmentsServicePublicPageContext);

    if (freeSlotsOnSelectedDate === null) {
        return null;
    } else {
        return <Center height="100%">
            <NonCustomAppointmentsServicePublicPageAppointmentMakerButton />
        </Center>;
    }
}