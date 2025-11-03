import { Calendar, SlotInfo } from "react-big-calendar";
import { localizer } from "../../../../common/localizer";
import { useContextOrThrow } from "../../../../utils/useContextOrThrow";

import { Box } from "@chakra-ui/react";
import { LocalTime } from "js-joda";
import { Result } from "neverthrow";
import "react-big-calendar/lib/css/react-big-calendar.css";
import { servicesApi } from "../../../../api/services-api";
import { useIntParam } from "../../../../hooks/useIntParam";
import { createDateWithoutTime } from "../../../../utils/date";
import { errorErrResultAsyncFromPromise } from "../../../../utils/result";
import { toastError } from "../../../../utils/toast";
import { CustomAppointmentsServicePublicPageContext } from "./CustomAppointmentsServicePublicPageContextValue";

export const CustomAppointmentsServicePublicPageCalendar = () => {
    const serviceId = useIntParam("serviceId");

    const { selectedDate, setSelectedDate, setFreeTimeWindowsOnSelectedDate } = useContextOrThrow(CustomAppointmentsServicePublicPageContext);

    const onSelectSlot = async (slot: SlotInfo) => {
        const date = createDateWithoutTime(slot.start);
        setSelectedDate(date);

        const promise = servicesApi.getFreeTimeWindowsForCustomAppointments(serviceId, date);
        const result: Result<[LocalTime, LocalTime][], Error> = await errorErrResultAsyncFromPromise(promise)
            .map(items => items.map(item => [
                LocalTime.parse(item.startTime),
                LocalTime.parse(item.endTime)
            ]));

        if (result.isErr()) {
            toastError("Unexpected error while loading free slots");
            return;
        }
        setFreeTimeWindowsOnSelectedDate(result.value);
    }

    const dayPropGetter = (date: Date) => {
        return {
            style: {
                backgroundColor:
                    selectedDate?.toDateString() === date.toDateString()
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
