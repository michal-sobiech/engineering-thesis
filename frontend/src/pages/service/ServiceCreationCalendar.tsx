import { Box } from "@chakra-ui/react";
import { FC, JSX } from "react";
import { BooleanToggle } from "../../common/BooleanToggle";
import { EventWithId } from "../../common/calendar/EventWithId";
import { WeeklyCalendar } from "../../common/calendar/WeeklyCalendar";
import { WeeklyCalendarWithAutoDivide } from "../../common/calendar/WeeklyCalendarWithAutoDivide";
import { StandardFloatInput } from "../../common/StandardFloatInput";
import { StandardLabeledContainer } from "../../common/StandardLabeledContainer";
import { UseStateSetter } from "../../utils/useState";

export interface ServiceCreationCalendarProps {
    areCustomAppointmentsEnabled: boolean,
    setAreCustomAppointmentsEnabled: UseStateSetter<boolean>,
    appointmentDurationMinutes: number | null
    setAppointmentDurationMinutes: UseStateSetter<number | null>,
    events: EventWithId[],
    setEvents: UseStateSetter<EventWithId[]>,
}

export const ServiceCreationCalendar: FC<ServiceCreationCalendarProps> = (props) => {
    const setAreCustomAppointmentsEnabledWrapper = (value: boolean) => {
        props.setEvents([]);
        props.setAreCustomAppointmentsEnabled(value);
    }

    let calendarComponent: JSX.Element;
    if (props.areCustomAppointmentsEnabled) {
        calendarComponent = (
            <Box maxHeight="50vh" overflowY="scroll">
                <WeeklyCalendar
                    events={props.events}
                    setEvents={props.setEvents}
                />
            </Box>
        );
    } else {
        calendarComponent = <>
            <StandardLabeledContainer label="Choose appointment length (in minutes)">
                <StandardFloatInput value={props.appointmentDurationMinutes} setValue={props.setAppointmentDurationMinutes} min={0} precision={0} step={5} />
            </StandardLabeledContainer>

            <Box maxHeight="50vh" overflowY="scroll">
                <WeeklyCalendarWithAutoDivide
                    events={props.events}
                    setEvents={props.setEvents}
                    eventDuration={{ minutes: props.appointmentDurationMinutes ?? undefined }}
                />
            </Box>
        </>;
    }

    return <>
        <StandardLabeledContainer label="Custom appointments?">
            <BooleanToggle
                option1Text="No"
                option2Text="Yes"
                isOption1Chosen={!props.areCustomAppointmentsEnabled}
                setIsOption1Chosen={value => setAreCustomAppointmentsEnabledWrapper(!value)}
            />
        </StandardLabeledContainer>
        {calendarComponent}
    </>;

}