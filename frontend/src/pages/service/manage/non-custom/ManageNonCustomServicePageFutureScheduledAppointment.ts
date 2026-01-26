import { LocalDateTime } from "@js-joda/core";

export interface ManageNonCustomServicePageFutureScheduledAppointment {
    appointmentId: number;
    username: string;
    userFirstName: string;
    userLastName: string;
    address: string;
    startDatetimeServiceLocal: LocalDateTime;
    endDatetimeServiceLocal: LocalDateTime;
    timezone: string;
    price: number;
    currency: string;
    isPaid: boolean;
}