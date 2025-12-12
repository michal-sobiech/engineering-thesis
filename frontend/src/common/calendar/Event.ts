import { Event as DefaultEvent } from "react-big-calendar";

export interface Event extends DefaultEvent {
    start: Date;
    end: Date;
}