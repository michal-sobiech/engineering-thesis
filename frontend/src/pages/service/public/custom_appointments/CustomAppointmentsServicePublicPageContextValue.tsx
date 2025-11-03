import { LocalTime } from "js-joda";
import { createContext } from "react";
import { UseStateSetter } from "../../../../utils/useState";

export interface CustomAppointmentsServicePublicPageContextValue {
    selectedDate: Date | null;
    setSelectedDate: UseStateSetter<Date | null>;
    freeTimeWindowsOnSelectedDate: [LocalTime, LocalTime][] | null;
    setFreeTimeWindowsOnSelectedDate: UseStateSetter<[LocalTime, LocalTime][] | null>;
    selectedTimeWindowStart: LocalTime | null;
    setSelectedTimeWindowStart: UseStateSetter<LocalTime | null>;
    selectedTimeWindowEnd: LocalTime | null;
    setSelectedTimeWindowEnd: UseStateSetter<LocalTime | null>;
}

export const CustomAppointmentsServicePublicPageContext = createContext<CustomAppointmentsServicePublicPageContextValue | null>(null);