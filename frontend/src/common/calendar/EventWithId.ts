import { Event } from "react-big-calendar";

export interface EventWithId extends Event {
    start: Date;
    end: Date;
    resource: { tempId: string };
}