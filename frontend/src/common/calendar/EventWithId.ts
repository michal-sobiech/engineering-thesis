import { Event } from "react-big-calendar";

export interface EventWithId extends Event {
    start: Date;
    end: Date;
    resource: { instanceId: string };
}

export function createEventWithId(start: Date, end: Date): EventWithId {
    return {
        start,
        end,
        resource: {
            instanceId: crypto.randomUUID(),
        }
    };
}