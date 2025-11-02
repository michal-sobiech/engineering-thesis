import { Box, Center, Flex } from "@chakra-ui/react"
import { LocalTime } from "js-joda"
import { TimeIntervalsDisplay } from "../../../../common/TimeIntervalsDisplay"
import { TimeRangePicker } from "../../../../common/TimeRangePicker"
import { extractLocalTimeFromDate } from "../../../../utils/date"
import { useContextOrThrow } from "../../../../utils/useContextOrThrow"
import { CustomAppointmentsServicePublicPageAppointmentMakerButton } from "./CustomAppointmentsServicePublicPageAppointmentMakerButton"
import { CustomAppointmentsServicePublicPageContext } from "./CustomAppointmentsServicePublicPageContextValue"

export const CustomAppointmentsServicePublicPageAppointmentMaker = () => {
    const {
        freeTimeWindowsOnSelectedDate,
        selectedTimeWindowStart,
        setSelectedTimeWindowStart,
        selectedTimeWindowEnd,
        setSelectedTimeWindowEnd,
    } = useContextOrThrow(CustomAppointmentsServicePublicPageContext);

    const freeTimeWindowsLocalTimes: [LocalTime, LocalTime][] | undefined = freeTimeWindowsOnSelectedDate?.map(window => ([
        extractLocalTimeFromDate(window[0]),
        extractLocalTimeFromDate(window[1]),
    ]));

    if (freeTimeWindowsLocalTimes === undefined) {
        return null;
    } else {
        return <Flex direction="row">
            <Box flex="1">
                <TimeIntervalsDisplay intervals={freeTimeWindowsLocalTimes} resolutionMinutes={30} />
            </Box>
            <Center flex="1" height="100%">
                <Flex direction="column">
                    <TimeRangePicker time1={selectedTimeWindowStart} setTime1={setSelectedTimeWindowStart} time2={selectedTimeWindowEnd} setTime2={setSelectedTimeWindowEnd} />
                    <CustomAppointmentsServicePublicPageAppointmentMakerButton />
                </Flex>
            </Center>
        </Flex>
    }
}