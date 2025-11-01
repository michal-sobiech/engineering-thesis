import { createContext } from "react";
import { UseStateSetter } from "../../../../utils/useState";

export interface NoCustomAppointmentsServicePublicPageContextValue {
    selectedDate: Date | null;
    setSelectedDate: UseStateSetter<Date | null>;
    freeAppointmentsOnSelectedDate: [Date, Date][] | null;
    setFreeAppointmentsOnSelectedDate: UseStateSetter<[Date, Date][] | null>;
    selectedAppointment: [Date, Date] | null;
    setSelectedAppointment: UseStateSetter<[Date, Date] | null>;
}

export const NoCustomAppointmentsServicePublicPageContext = createContext<NoCustomAppointmentsServicePublicPageContextValue | null>(null);