import { Event } from "react-big-calendar";

export interface EventWithId extends Event {
    resource: { tempId: string };
}