import { WeeklyTimeWindow } from "../../../utils/WeeklyTimeWindow";
import { WeeklyTimeWindowWithCapacity } from "../../../utils/WeeklyTimeWindowWithCapacity";

export type WeeklyTimeWindows = WeeklyTimeWindowsNonCustom | WeeklyTimeWindowsCustom;

export interface WeeklyTimeWindowsNonCustom {
    custom: false;
    appointmentDurationMinutes: number | null;
    windows: WeeklyTimeWindowWithCapacity[];
}

export interface WeeklyTimeWindowsCustom {
    custom: true;
    windows: WeeklyTimeWindow[]
}