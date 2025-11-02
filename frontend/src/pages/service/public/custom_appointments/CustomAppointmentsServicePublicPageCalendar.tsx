import { Calendar, SlotInfo } from "react-big-calendar";
import { localizer } from "../../../../common/localizer";
import { useContextOrThrow } from "../../../../utils/useContextOrThrow";

import { Box } from "@chakra-ui/react";
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

        const promise = servicesApi.getFreeTimeWindowsForCustomAppointments(serviceId);
        const result = await errorErrResultAsyncFromPromise(promise);
        if (result.isErr()) {
            toastError("Unexpected error while loading free slots");
            return;
        }
        const freeTimeWindows: [Date, Date][] = result.value.map(timeWindow => [timeWindow.startDatetime, timeWindow.endDatetime]);
        setFreeTimeWindowsOnSelectedDate(freeTimeWindows);
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
