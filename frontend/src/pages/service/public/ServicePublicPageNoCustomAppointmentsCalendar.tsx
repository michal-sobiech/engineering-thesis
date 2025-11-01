import { Calendar, SlotInfo } from "react-big-calendar";
import { localizer } from "../../../common/localizer";
import { useContextOrThrow } from "../../../utils/useContextOrThrow";
import { ServicePublicPageContext } from "./ServicePublicPageContext";

import { Box } from "@chakra-ui/react";
import { ResultAsync } from "neverthrow";
import "react-big-calendar/lib/css/react-big-calendar.css";
import { servicesApi } from "../../../api/services-api";
import { GetServiceFreeNonCustomAppointmentsResponseItem } from "../../../GENERATED-api";
import { useIntParam } from "../../../hooks/useIntParam";
import { createDateWithoutTime } from "../../../utils/date";
import { errorErrResultAsyncFromPromise } from "../../../utils/result";
import { toastError } from "../../../utils/toast";

export const ServicePublicPageNoCustomAppointmentsCalendar = () => {
    const serviceId = useIntParam("serviceId");

    const { selectedDate, setSelectedDate, setFreeAppointmentsOnSelectedDate } = useContextOrThrow(ServicePublicPageContext);

    const onSelectSlot = async (slot: SlotInfo) => {
        const date = createDateWithoutTime(slot.start);
        setSelectedDate(date);

        const freeAppointments = await fetchFreeAppointmentsOnDay(date);
        if (freeAppointments.isErr()) {
            toastError("Unexpected error while loading free slots");
            return;
        }
        const freeAppointmentsTuples: [Date, Date][] = freeAppointments.value.map(appointment => [appointment.startDatetime, appointment.endDatetime]);
        setFreeAppointmentsOnSelectedDate(freeAppointmentsTuples);
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

    function fetchFreeAppointmentsOnDay(day: Date): ResultAsync<GetServiceFreeNonCustomAppointmentsResponseItem[], Error> {
        const promise = servicesApi.getFreeNonCustomAppointments(serviceId);
        return errorErrResultAsyncFromPromise(promise);
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
