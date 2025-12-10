
import { Box } from "@chakra-ui/react";
import "react-big-calendar/lib/css/react-big-calendar.css";
import { useServicesApi } from "../../../../api/services-api";
import { WeeklyCalendar } from "../../../../common/calendar/weekly/WeeklyCalendar";
import { useServiceIdFromLoader } from "../../../../common/loader/service-id-loader";
import { useContextOrThrow } from "../../../../hooks/useContextOrThrow";
import { StaffCustomServicePageContext } from "./StaffCustomServicePageContext";

export const EditableWeeklyCustomServiceCalendar = () => {
    const servicesApi = useServicesApi();
    const serviceId = useServiceIdFromLoader();

    const { events, setEvents } = useContextOrThrow(StaffCustomServicePageContext);

    const onSelect = 

    return <Box height="100%">
        <WeeklyCalendar events={events} setEvents={setEvents} />
    </Box>;
}