import { LocalDate, LocalTime } from "@js-joda/core";
import { EnterpriseServicesApi } from "../GENERATED-api";
import { createUtcDateFromLocalDate } from "../utils/date";

export async function fetchFreeAppointmentsOnDateInServiceTimezone(serviceId: number, localDate: LocalDate, servicesApi: EnterpriseServicesApi): Promise<[LocalTime, LocalTime][]> {
    const date = createUtcDateFromLocalDate(localDate);
    const response = await servicesApi.getFreeNonCustomAppointments(serviceId, date);
    return response.map(item => [
        LocalTime.parse(item.startTime),
        LocalTime.parse(item.endTime)
    ]);
}

export async function fetchFreeTimeWindowsForCustomAppointmentsOnLocalDate(serviceId: number, localDate: LocalDate, servicesApi: EnterpriseServicesApi): Promise<[LocalTime, LocalTime][]> {
    const date = createUtcDateFromLocalDate(localDate);
    const response = await servicesApi.getFreeTimeWindowsForCustomAppointments(serviceId, date);
    return response.map(item => [
        LocalTime.parse(item.startTime),
        LocalTime.parse(item.endTime)
    ]);
}