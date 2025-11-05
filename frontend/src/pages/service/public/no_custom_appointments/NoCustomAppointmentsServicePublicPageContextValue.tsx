import { LocalDate, LocalTime } from "@js-joda/core";
import { createContext } from "react";
import { UseStateSetter } from "../../../../utils/useState";

export interface NoCustomAppointmentsServicePublicPageContextValue {
    selectedDate: LocalDate | null;
    setSelectedDate: UseStateSetter<LocalDate | null>;
    freeSlotsOnSelectedDate: [LocalTime, LocalTime][] | null;
    setFreeSlotsOnSelectedDate: UseStateSetter<[LocalTime, LocalTime][] | null>;
    selectedSlot: [LocalTime, LocalTime] | null;
    setSelectedSlot: UseStateSetter<[LocalTime, LocalTime] | null>;
}

export const NoCustomAppointmentsServicePublicPageContext = createContext<NoCustomAppointmentsServicePublicPageContextValue | null>(null);