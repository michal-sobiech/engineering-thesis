import { EventWithId } from "../../../common/calendar/EventWithId";
import { EventWithIdAndCapacity } from "../../../common/calendar/EventWithIdAndCapacity";

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