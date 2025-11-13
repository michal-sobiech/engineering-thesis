import { Box } from "@chakra-ui/react";
import { FC, JSX } from "react";
import { BooleanToggle } from "../../common/BooleanToggle";
import { EventWithId } from "../../common/calendar/EventWithId";
import { EventWithIdAndCapacity } from "../../common/calendar/EventWithIdAndCapacity";
import { WeeklyCalendar } from "../../common/calendar/WeeklyCalendar";
import { StandardFloatInput } from "../../common/StandardFloatInput";
import { StandardLabeledContainer } from "../../common/StandardLabeledContainer";
import { UseStateSetter } from "../../utils/use-state";
import { CustomOrNotAppointmentsEvents } from "./calendar/CustomAppointmentsEvents";
import { WeeklyCalendarCustomAppoinmentsDisabled } from "./calendar/WeeklyCalendarCustomAppointmentsDisabled";

export interface ServiceCreationCalendarProps {
    appointmentDurationMinutes: number | null
    setAppointmentDurationMinutes: UseStateSetter<number | null>,
    eventsData: CustomOrNotAppointmentsEvents;
    setEventsData: UseStateSetter<CustomOrNotAppointmentsEvents>,
}

export const ServiceCreationCalendar: FC<ServiceCreationCalendarProps> = ({ appointmentDurationMinutes, setAppointmentDurationMinutes, eventsData, setEventsData }) => {
    let calendarComponent: JSX.Element;
    if (eventsData.areCustomAppointmentsEnabled) {
        const setEventsWrapper = (valueOrUpdater: (EventWithId[] | ((prevEvents: EventWithId[]) => EventWithId[]))) => {
            if (typeof valueOrUpdater === "function") {
                const updater = valueOrUpdater as (prevEvents: EventWithId[]) => EventWithId[];
                setEventsData(prevEventsData => {
                    const newEvents = updater(prevEventsData.events);
                    return {
                        areCustomAppointmentsEnabled: true,
                        events: newEvents,
                    };
                });
            } else {
                const value = valueOrUpdater as EventWithId[];
                setEventsData({
                    areCustomAppointmentsEnabled: true,
                    events: value,
                });
            }
        }

        calendarComponent = (
            <Box maxHeight="50%" overflowY="scroll">
                <WeeklyCalendar
                    events={eventsData.events}
                    setEvents={setEventsWrapper}
                />
            </Box>
        );
    } else {

        const setEventsWrapper = (valueOrUpdater: (EventWithIdAndCapacity[] | ((prevEvents: EventWithIdAndCapacity[]) => EventWithIdAndCapacity[]))) => {
            if (typeof valueOrUpdater === "function") {
                const updater = valueOrUpdater as (prevEvents: EventWithIdAndCapacity[]) => EventWithIdAndCapacity[];
                setEventsData(prevEventsData => {
                    const newEvents = updater(prevEventsData.events as EventWithIdAndCapacity[]);
                    return {
                        areCustomAppointmentsEnabled: false,
                        events: newEvents,
                    };
                });
            } else {
                const value = valueOrUpdater as EventWithIdAndCapacity[];
                setEventsData({
                    areCustomAppointmentsEnabled: false,
                    events: value,
                });
            }
        }

        calendarComponent = <>
            <StandardLabeledContainer label="Choose appointment length (in minutes)">
                <StandardFloatInput value={appointmentDurationMinutes} setValue={setAppointmentDurationMinutes} min={0} precision={0} step={5} />
            </StandardLabeledContainer>

            <Box maxHeight="50%" overflowY="scroll">
                <WeeklyCalendarCustomAppoinmentsDisabled
                    events={eventsData.events}
                    setEvents={setEventsWrapper}
                    eventDuration={{ minutes: appointmentDurationMinutes ?? undefined }}
                />
            </Box>
        </>;
    }

    const setAreCustomAppointmentsEnabledWrapper = (enabled: boolean) => {
        setEventsData({ areCustomAppointmentsEnabled: enabled, events: [] });
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
        {calendarComponent}
    </>;

}