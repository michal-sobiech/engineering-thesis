import { EventWithId } from "../../../common/calendar/EventWithId";
import { EventWithIdAndCapacity } from "../../../common/calendar/EventWithIdAndCapacity";

export interface CustomAppointmentsEnabledEvents {
    areCustomAppointmentsEnabled: true;
    events: EventWithId[];
}

export interface CustomAppointmentsDisabledEvents {
    areCustomAppointmentsEnabled: false;
    events: EventWithIdAndCapacity[];
}

export type CustomAppointmentsEvents = CustomAppointmentsEnabledEvents | CustomAppointmentsDisabledEvents;