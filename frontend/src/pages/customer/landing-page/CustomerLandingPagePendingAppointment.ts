import { LocalDateTime } from "@js-joda/core";

export interface CustomerLandingPagePendingAppointment {
    appointmentId: number;
    serviceId: number;
    serviceName: string,
    enterpriseName: string;
    address: string;
    startDatetimeServiceLocal: LocalDateTime;
    endDatetimeServiceLocal: LocalDateTime;
    timezone: string;
    price: number | null;
    currencyIso: string;
}