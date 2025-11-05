import { Calendar, SlotInfo } from "react-big-calendar";
import { localizer } from "../../../../common/localizer";
import { useContextOrThrow } from "../../../../utils/useContextOrThrow";

import { Box } from "@chakra-ui/react";
import { LocalDate, LocalTime } from "@js-joda/core";
import { ResultAsync } from "neverthrow";
import "react-big-calendar/lib/css/react-big-calendar.css";
import { servicesApi } from "../../../../api/services-api";
import { useIntParam } from "../../../../hooks/useIntParam";
import { createUtcDateFromLocalDate, extractLocalDateFromDate } from "../../../../utils/date";
import { errorErrResultAsyncFromPromise } from "../../../../utils/result";
import { toastError } from "../../../../utils/toast";
import { CustomAppointmentsServicePublicPageContext } from "./CustomAppointmentsServicePublicPageContextValue";

export const CustomAppointmentsServicePublicPageCalendar = () => {
    const serviceId = useIntParam("serviceId");

    const { selectedDate, setSelectedDate, setFreeTimeWindowsOnSelectedDate } = useContextOrThrow(CustomAppointmentsServicePublicPageContext);

    const onSelectSlot = async (slot: SlotInfo) => {
        const slotDate = slot.start;
        const slotLocalDate = extractLocalDateFromDate(slotDate);
        setSelectedDate(slotLocalDate);

        const result = await getFreeTimeWindowsForCustomAppointmentsOnLocalDate(slotLocalDate);
        if (result.isErr()) {
            toastError("Unexpected error while loading free slots");
            return;
        }
        setFreeTimeWindowsOnSelectedDate(result.value);
    }

    function getFreeTimeWindowsForCustomAppointmentsOnLocalDate(localDate: LocalDate): ResultAsync<[LocalTime, LocalTime][], Error> {
        const date = createUtcDateFromLocalDate(localDate);
        const promise = servicesApi.getFreeTimeWindowsForCustomAppointments(serviceId, date);
        return errorErrResultAsyncFromPromise(promise)
            .map(items => items.map(item => [
                LocalTime.parse(item.startTime),
                LocalTime.parse(item.endTime)
            ]));
    }

    const dayPropGetter = (date: Date) => {
        const localDate = extractLocalDateFromDate(date);
        return {
            style: {
                backgroundColor:
                    selectedDate?.equals(selectedDate)
                        ? "#6284ebff"
                        : undefined
            }
        }
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
