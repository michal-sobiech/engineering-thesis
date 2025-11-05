import { LocalDate, LocalTime } from "@js-joda/core";
import { createContext } from "react";
import { UseStateSetter } from "../../../../utils/useState";

export interface CustomAppointmentsServicePublicPageContextValue {
    selectedDate: LocalDate | null;
    setSelectedDate: UseStateSetter<LocalDate | null>;
    freeTimeWindowsOnSelectedDate: [LocalTime, LocalTime][] | null;
    setFreeTimeWindowsOnSelectedDate: UseStateSetter<[LocalTime, LocalTime][] | null>;
    selectedTimeWindowStart: LocalTime | null;
    setSelectedTimeWindowStart: UseStateSetter<LocalTime | null>;
    selectedTimeWindowEnd: LocalTime | null;
    setSelectedTimeWindowEnd: UseStateSetter<LocalTime | null>;
}

export const CustomAppointmentsServicePublicPageContext = createContext<CustomAppointmentsServicePublicPageContextValue | null>(null);