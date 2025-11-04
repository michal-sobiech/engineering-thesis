import { Center } from "@chakra-ui/react";
import { useContextOrThrow } from "../../../../utils/useContextOrThrow";
import { NonCustomAppointmentsServicePublicPageAppointmentMakerButton } from "./CustomAppointmentsServicePublicPageAppointmentMakerButton";
import { NoCustomAppointmentsServicePublicPageContext } from "./NoCustomAppointmentsServicePublicPageContextValue";

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