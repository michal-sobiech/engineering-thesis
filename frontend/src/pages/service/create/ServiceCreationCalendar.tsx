import { Box } from "@chakra-ui/react";
import { FC } from "react";
import { BooleanToggle } from "../../../common/BooleanToggle";
import { EditableCustomWeeklySchedule } from "../../../common/calendar/weekly/editable/EditableCustomWeeklySchedule";
import { EditableNonCustomWeeklySchedule } from "../../../common/calendar/weekly/editable/EditableNonCustomWeeklySchedule";
import { StandardFloatInput } from "../../../common/StandardFloatInput";
import { StandardLabeledContainer } from "../../../common/StandardLabeledContainer";
import { StateUpdater } from "../../../utils/StateUpdater";
import { WeeklyTimeWindow } from "../../../utils/WeeklyTimeWindow";
import { WeeklyTimeWindowWithCapacity } from "../../../utils/WeeklyTimeWindowWithCapacity";
import { WeeklyTimeWindows, WeeklyTimeWindowsCustom, WeeklyTimeWindowsNonCustom } from "../calendar/WeeklyTimeWindows";

export interface ServiceCreationCalendarProps {
    windowsData: WeeklyTimeWindows;
    setWindowsData: StateUpdater<WeeklyTimeWindows>;
}

export const ServiceCreationCalendar: FC<ServiceCreationCalendarProps> = ({ windowsData, setWindowsData }) => {
    const setCustomWrapper = (enabled: boolean) => {
        if (enabled) {
            setWindowsData(_ => ({
                custom: true,
                windows: [],
            }));
        } else {
            setWindowsData(_ => ({
                custom: false,
                windows: [],
                appointmentDurationMinutes: null
            }));
        }
    }

    return <>
        <StandardLabeledContainer label="Custom appointments?">
            <BooleanToggle
                option1Text="No"
                option2Text="Yes"
                isOption1Chosen={!windowsData.custom}
                setIsOption1Chosen={enabled => setCustomWrapper(!enabled)}
            />
        </StandardLabeledContainer>
        <Calendar windowsData={windowsData} setWindowsData={setWindowsData} />
    </>;

}

interface CalendarProps {
    windowsData: WeeklyTimeWindows,
    setWindowsData: StateUpdater<WeeklyTimeWindows>,
}

const Calendar: FC<CalendarProps> = ({ windowsData, setWindowsData }) => {
    if (windowsData.custom) {
        const setWindows: StateUpdater<WeeklyTimeWindow[]> = (updater) => {
            setWindowsData(prevUncast => {
                const prev = prevUncast as WeeklyTimeWindowsCustom;

                const updated = { ...prev };
                const newWindows = updater(prev.windows);
                updated.windows = newWindows;

                return updated;
            });
        }

        return <Box maxHeight="100%" overflowY="scroll">
            <EditableCustomWeeklySchedule
                windows={windowsData.windows}
                setWindows={setWindows}
            />
        </Box>;
    }

    const setEvents: StateUpdater<WeeklyTimeWindowWithCapacity[]> = (updateFn) => {
        setWindowsData(prevUncast => {
            const prev = prevUncast as WeeklyTimeWindowsNonCustom;

            const updated = { ...prev };
            const updatedWindows = updateFn(prev.windows);
            updated.windows = updatedWindows

            return updated;
        });
    }

    const setAppointmentDurationMinutes = (value: number) => {
        setWindowsData(prevUncast => {
            const prev = prevUncast as WeeklyTimeWindowsNonCustom;

            const updated = { ...prev };
            updated.appointmentDurationMinutes = value;
            return updated;
        });
    }

    return <>
        <StandardLabeledContainer label="Choose appointment length (in minutes)">
            <StandardFloatInput
                value={windowsData.appointmentDurationMinutes}
                setValue={setAppointmentDurationMinutes}
                min={0}
                precision={0}
                step={5} />
        </StandardLabeledContainer>

        <Box maxHeight="50%" overflowY="scroll">
            <EditableNonCustomWeeklySchedule
                windows={windowsData.windows}
                setWindows={setEvents}
                appointmentDurationMinutes={windowsData.appointmentDurationMinutes ?? null}
            />
        </Box>
    </>;
};
