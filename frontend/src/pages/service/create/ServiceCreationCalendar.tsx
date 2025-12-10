import { Box } from "@chakra-ui/react";
import { FC } from "react";
import { BooleanToggle } from "../../../common/BooleanToggle";
import { EventWithId } from "../../../common/calendar/EventWithId";
import { EventWithIdAndCapacity } from "../../../common/calendar/EventWithIdAndCapacity";
import { EditableCustomWeeklyCalendar } from "../../../common/calendar/weekly/editable/EditableCustomWeeklyCalendar";
import { EditableNonCustomWeeklyCalendar } from "../../../common/calendar/weekly/editable/EditableNonCustomWeeklyCalendar";
import { StandardFloatInput } from "../../../common/StandardFloatInput";
import { StandardLabeledContainer } from "../../../common/StandardLabeledContainer";
import { StateUpdater } from "../../../utils/StateUpdater";
import { Events, EventsCustom, EventsNonCustom } from "../calendar/Events";

export interface ServiceCreationCalendarProps {
    eventsData: Events;
    setEventsData: StateUpdater<Events>;
}

export const ServiceCreationCalendar: FC<ServiceCreationCalendarProps> = ({ eventsData, setEventsData }) => {
    const setAreCustomAppointmentsEnabledWrapper = (enabled: boolean) => {
        if (enabled) {
            setEventsData(_ => ({
                areCustomAppointmentsEnabled: true,
                events: [],
            }));
        } else {
            setEventsData(_ => ({
                areCustomAppointmentsEnabled: false,
                events: [],
                appointmentDurationMinutes: null
            }));
        }
    }

    return <>
        <StandardLabeledContainer label="Custom appointments?">
            <BooleanToggle
                option1Text="No"
                option2Text="Yes"
                isOption1Chosen={!eventsData.areCustomAppointmentsEnabled}
                setIsOption1Chosen={enabled => setAreCustomAppointmentsEnabledWrapper(!enabled)}
            />
        </StandardLabeledContainer>
        <Calendar eventsData={eventsData} setEventsData={setEventsData} />
    </>;

}

const Calendar = ({ eventsData, setEventsData }: { eventsData: Events, setEventsData: StateUpdater<Events> }) => {
    if (eventsData.areCustomAppointmentsEnabled) {
        const setEvents: StateUpdater<EventWithId[]> = (updater) => {
            setEventsData(prevEventsDataUncast => {
                const prevEventsData = prevEventsDataUncast as EventsCustom;

                const newEventsData = { ...prevEventsData };
                const newEvents = updater(prevEventsData.events);
                newEventsData.events = newEvents;

                return newEventsData;
            });
        }

        return <Box maxHeight="100%" overflowY="scroll">
            <EditableCustomWeeklyCalendar
                events={eventsData.events}
                setEvents={setEvents}
            />
        </Box>;
    }

    const setEvents: StateUpdater<EventWithIdAndCapacity[]> = (updateFn) => {
        setEventsData(prevEventsDataUncasted => {
            const prevEventsData = prevEventsDataUncasted as EventsNonCustom;

            const newEventsData = { ...prevEventsData };
            const newEvents = updateFn(prevEventsData.events);
            newEventsData.events = newEvents;

            return newEventsData;
        });
    }


    const setAppointmentDurationMinutes = (value: number) => {
        setEventsData(prevEventsDataUncast => {
            const prevEventsData = prevEventsDataUncast as EventsNonCustom;

            const newEventsData = { ...prevEventsData };
            newEventsData.appointmentDurationMinutes = value;
            return newEventsData;
        });
    }

    return <>
        <StandardLabeledContainer label="Choose appointment length (in minutes)">
            <StandardFloatInput
                value={eventsData.appointmentDurationMinutes}
                setValue={setAppointmentDurationMinutes}
                min={0}
                precision={0}
                step={5} />
        </StandardLabeledContainer>

        <Box maxHeight="50%" overflowY="scroll">
            <EditableNonCustomWeeklyCalendar
                events={eventsData.events}
                setEvents={setEvents}
                appointmentDurationMinutes={eventsData.appointmentDurationMinutes ?? null}
            />
        </Box>
    </>;
};
