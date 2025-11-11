import { Calendar, SlotInfo } from "react-big-calendar";
import { localizer } from "../../../../common/localizer";
import { useContextOrThrow } from "../../../../hooks/useContextOrThrow";
import { NoCustomAppointmentsServicePublicPageContext } from "./NoCustomAppointmentsServicePublicPageContextValue";

import { Box } from "@chakra-ui/react";
import { LocalDate, LocalTime } from "@js-joda/core";
import { ResultAsync } from "neverthrow";
import "react-big-calendar/lib/css/react-big-calendar.css";
import { servicesApi } from "../../../../api/services-api";
import { useIntParam } from "../../../../hooks/useIntParam";
import { createUtcDateFromLocalDate, extractLocalDateFromDate } from "../../../../utils/date";
import { errorErrResultAsyncFromPromise } from "../../../../utils/result";
import { toastError } from "../../../../utils/toast";

export const NoCustomAppointmentsServicePublicPageCalendar = () => {
    const serviceId = useIntParam("serviceId");

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

    return <Box height="40vh">
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
