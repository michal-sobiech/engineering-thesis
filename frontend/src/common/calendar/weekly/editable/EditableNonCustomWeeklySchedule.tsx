import { Box } from "@chakra-ui/react";
import { DayOfWeek, Duration, LocalTime } from "@js-joda/core";
import { FC, useState } from "react";
import { splitLocalTimePeriod } from "../../../../utils/date";
import { Position } from "../../../../utils/Position";
import { StateUpdater } from "../../../../utils/StateUpdater";
import { WeeklyTimeWindow } from "../../../../utils/WeeklyTimeWindow";
import { WeeklyTimeWindowWithCapacity } from "../../../../utils/WeeklyTimeWindowWithCapacity";
import { EditableNonCustomWeeklyCalendarPopup } from "./EditableNonCustomWeeklyCalendarPopup";
import { EditableWeeklySchedule } from "./EditableWeeklySchedule";

export interface EditableNonCustomWeeklyScheduleProps {
    appointmentDurationMinutes: number | null
    windows: WeeklyTimeWindowWithCapacity[];
    setWindows: StateUpdater<WeeklyTimeWindowWithCapacity[]>;
}

export const EditableNonCustomWeeklySchedule: FC<EditableNonCustomWeeklyScheduleProps> = ({ appointmentDurationMinutes, windows, setWindows }) => {
    const [lastClickPos, setLastClickPos] = useState<Position | null>(null);
    const [selectedWindow, setSelectedWindow] = useState<WeeklyTimeWindowWithCapacity | null>(null);

    const onSelectSlot = (slot: WeeklyTimeWindow) => {
        const duration = appointmentDurationMinutes === null
            ? null
            : Duration.ofMinutes(appointmentDurationMinutes);

        let windows: WeeklyTimeWindow[];
        if (duration === null) {
            windows = [slot];
        } else {
            windows = splitLocalTimePeriod(slot.start, slot.end, duration)
                .map(window => ({
                    dayOfWeek: slot.dayOfWeek,
                    start: window[0],
                    end: window[1]
                }));
        }

        const windowsWithCapacity: WeeklyTimeWindowWithCapacity[] = windows.map(window => ({
            ...window,
            capacity: 1,
        }));

        setWindows(prev => [...prev, ...windowsWithCapacity]);
    };

    const onSelectEvent = (dayOfWeek: DayOfWeek, start: LocalTime, end: LocalTime, e: React.SyntheticEvent) => {
        const window = windows.find(window => {
            return (
                window.dayOfWeek.equals(dayOfWeek)
                && window.start.equals(start)
                && window.end.equals(end)
            );
        });
        const mouseEvent = e as React.MouseEvent;
        setLastClickPos({
            x: mouseEvent.clientX,
            y: mouseEvent.clientY,
        });
        setSelectedWindow(window ?? null);
    }

    function deleteEvent(dayOfWeek: DayOfWeek, start: LocalTime, end: LocalTime) {
        setWindows(prev => prev.filter(window => {
            return !(
                window.dayOfWeek.equals(dayOfWeek)
                && window.start.equals(start)
                && window.end.equals(end)
            );
        }));
    }

    let popup;
    if (selectedWindow === null) {
        popup = null;
    } else {
        const setCapacity = (value: number) => {
            setWindows(prev => prev.map(window => {
                if (window.dayOfWeek.equals(selectedWindow.dayOfWeek)
                    && window.start.equals(selectedWindow.start)
                    && window.end.equals(selectedWindow.end)) {
                    window.capacity = value;
                }
                return window;
            }));
        };

        const removeEvent = () => {
            deleteEvent(selectedWindow.dayOfWeek, selectedWindow.start, selectedWindow.end);
        }
        popup = <EditableNonCustomWeeklyCalendarPopup
            close={() => setSelectedWindow(null)}
            remove={removeEvent}
            position={lastClickPos ?? { x: 0, y: 0 }}
            capacity={selectedWindow.capacity}
            setCapacity={setCapacity}
        />;
    }

    return <Box position="relative">
        <EditableWeeklySchedule
            timeWindows={windows}
            onSelectSlot={onSelectSlot}
            onSelectEvent={onSelectEvent}
        />
        {popup}
    </Box >
};
