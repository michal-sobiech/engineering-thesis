import { EventWithId } from "../../../common/calendar/Event";
import { EventWithIdAndCapacity } from "../../../common/calendar/EventWithCapacity";

export type Events = EventsNonCustom | EventsCustom;

export interface EventsNonCustom {
    areCustomAppointmentsEnabled: false;
    appointmentDurationMinutes: number | null;
    events: EventWithIdAndCapacity[];
}

export interface EventsCustom {
    areCustomAppointmentsEnabled: true;
    events: EventWithId[];
}