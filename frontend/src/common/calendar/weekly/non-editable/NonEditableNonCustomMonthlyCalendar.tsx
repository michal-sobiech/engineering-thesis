import { Calendar, SlotInfo } from "react-big-calendar";
import { useContextOrThrow } from "../../../../hooks/useContextOrThrow";
import { NoCustomAppointmentsServicePublicPageContext } from "../../../../pages/service/public/no_custom_appointments/NoCustomAppointmentsServicePublicPageContextValue";
import { localizer } from "../../../localizer";

import { Box } from "@chakra-ui/react";
import { LocalDate, LocalTime } from "@js-joda/core";
import { ResultAsync } from "neverthrow";
import "react-big-calendar/lib/css/react-big-calendar.css";
import { useServicesApi } from "../../../../api/services-api";
import { createUtcDateFromLocalDate, extractLocalDateFromDate } from "../../../../utils/date";
import { errorErrResultAsyncFromPromise } from "../../../../utils/result";
import { toastError } from "../../../../utils/toast";
import { useServiceIdFromLoader } from "../../../loader/service-id-loader";

export const CustomAppointmentsServicePublicPageCalendar = () => {
    const servicesApi = useServicesApi();
    const serviceId = useServiceIdFromLoader();

    const { selectedDate, setSelectedDate, setFreeSlotsOnSelectedDate: setFreeAppointmentsOnSelectedDate } = useContextOrThrow(NoCustomAppointmentsServicePublicPageContext);

    const onSelectSlot = async (slot: SlotInfo) => {
        const slotDate = slot.start;
        const slotLocalDate = extractLocalDateFromDate(slotDate);
        setSelectedDate(slotLocalDate);

        const freeAppointments = await fetchFreeAppointmentsOnDateInServiceTimezone(slotLocalDate);
        if (freeAppointments.isErr()) {
            toastError("Unexpected error while loading free slots");
            return;
        }
        setFreeAppointmentsOnSelectedDate(freeAppointments.value);
    }

    const dayPropGetter = (date: Date) => {
        const localDate = extractLocalDateFromDate(date);
        return {
            style: {
                backgroundColor:
                    selectedDate?.equals(localDate)
                        ? "#6284ebff"
                        : undefined
            }
        }
    }

    function fetchFreeAppointmentsOnDateInServiceTimezone(localDate: LocalDate): ResultAsync<[LocalTime, LocalTime][], Error> {
        const date = createUtcDateFromLocalDate(localDate);
        const promise = servicesApi.getFreeNonCustomAppointments(serviceId, date);
        return errorErrResultAsyncFromPromise(promise)
            .map(items => items.map(item => [
                LocalTime.parse(item.startTime),
                LocalTime.parse(item.endTime)
            ]));
    }

    return <Box height="100%">
        <Calendar
            localizer={localizer}
            views={["month"]}
            defaultView="month"
            selectable
            onSelectSlot={onSelectSlot}
            style={{ height: "100%" }}
            dayPropGetter={dayPropGetter}
        />
    </Box>;
}
