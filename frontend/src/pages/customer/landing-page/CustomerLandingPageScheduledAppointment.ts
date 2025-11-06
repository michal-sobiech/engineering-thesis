import { LocalDateTime } from "@js-joda/core";

export interface CustomerLandingPageScheduledAppointment {
    serviceName: string,
    enterpriseName: string;
    address: string;
    startDatetimeServiceLocal: LocalDateTime;
    endDatetimeServiceLocal: LocalDateTime;
    timezone: string;
    price: number | null;
}