import { LocalTime } from "js-joda";
import { createContext } from "react";
import { UseStateSetter } from "../../../../utils/useState";

export interface NoCustomAppointmentsServicePublicPageContextValue {
    selectedDate: Date | null;
    setSelectedDate: UseStateSetter<Date | null>;
    freeSlotsOnSelectedDate: [LocalTime, LocalTime][] | null;
    setFreeSlotsOnSelectedDate: UseStateSetter<[LocalTime, LocalTime][] | null>;
    selectedSlot: [LocalTime, LocalTime] | null;
    setSelectedSlot: UseStateSetter<[LocalTime, LocalTime] | null>;
}

export const NoCustomAppointmentsServicePublicPageContext = createContext<NoCustomAppointmentsServicePublicPageContextValue | null>(null);