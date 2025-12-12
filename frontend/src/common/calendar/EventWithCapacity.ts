import { Event as DefaultEvent } from "react-big-calendar";

export interface EventWithCapacity extends DefaultEvent {
    start: Date;
    end: Date;
    resource: {
        capacity: number;
    };
}