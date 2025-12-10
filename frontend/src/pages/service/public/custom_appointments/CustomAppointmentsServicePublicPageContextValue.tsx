import { LocalDate, LocalTime } from "@js-joda/core";
import { createContext } from "react";
import { GeoPosition } from "../../../../utils/GeoPosition";
import { UseStateSetter } from "../../../../utils/use-state";

export interface CustomAppointmentsServicePublicPageContextValue {
    selectedDate: LocalDate | null;
    setSelectedDate: UseStateSetter<LocalDate | null>;
    freeTimeWindowsOnSelectedDate: [LocalTime, LocalTime][] | null;
    setFreeTimeWindowsOnSelectedDate: UseStateSetter<[LocalTime, LocalTime][] | null>;
    selectedTimeWindowStart: LocalTime | null;
    setSelectedTimeWindowStart: UseStateSetter<LocalTime | null>;
    selectedTimeWindowEnd: LocalTime | null;
    setSelectedTimeWindowEnd: UseStateSetter<LocalTime | null>;
    position: GeoPosition | null;
    setPosition: UseStateSetter<GeoPosition | null>;
    address: string;
    setAddress: UseStateSetter<string>;
}

export const CustomAppointmentsServicePublicPageContext = createContext<CustomAppointmentsServicePublicPageContextValue | null>(null);