import { LocalDateTime } from "@js-joda/core";

export interface CustomerLandingPageRejectedAppointment {
    serviceName: string,
    enterpriseName: string;
    address: string;
    startDatetimeServiceLocal: LocalDateTime;
    endDatetimeServiceLocal: LocalDateTime;
    timezone: string;
    price: number | null;
    rejectionMessage: string;
}