import { LocalTime } from "js-joda";
import { createContext } from "react";
import { UseStateSetter } from "../../../../utils/useState";

export interface NoCustomAppointmentsServicePublicPageContextValue {
    selectedDate: Date | null;
    setSelectedDate: UseStateSetter<Date | null>;
    freeAppointmentsOnSelectedDate: [LocalTime, LocalTime][] | null;
    setFreeAppointmentsOnSelectedDate: UseStateSetter<[LocalTime, LocalTime][] | null>;
    selectedAppointment: [LocalTime, LocalTime] | null;
    setSelectedAppointment: UseStateSetter<[LocalTime, LocalTime] | null>;
}

export const NoCustomAppointmentsServicePublicPageContext = createContext<NoCustomAppointmentsServicePublicPageContextValue | null>(null);