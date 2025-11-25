import { LocalDateTime } from "@js-joda/core";

export interface CustomerLandingPageRejectedAppointment {
    appointmentId: number;
    serviceName: string;
    enterpriseName: string;
    address: string;
    startDatetimeServiceLocal: LocalDateTime;
    endDatetimeServiceLocal: LocalDateTime;
    timezone: string;
    price: number | null;
    currencyIso: string;
    rejectionMessage: string;
}