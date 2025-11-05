import { Box, Center, Flex, Text } from "@chakra-ui/react"
import { LocalDate, LocalTime } from "@js-joda/core"
import { useState } from "react"
import { StandardFlex } from "../../../../common/StandardFlex"
import { StandardPanel } from "../../../../common/StandardPanel"
import { TimeIntervalsDisplay } from "../../../../common/TimeIntervalsDisplay"
import { CustomAppointmentsServicePublicPageAppointmentMaker } from "./CustomAppointmentsServicePublicPageAppointmentMaker"
import { CustomAppointmentsServicePublicPageCalendar } from "./CustomAppointmentsServicePublicPageCalendar"
import { CustomAppointmentsServicePublicPageContext, CustomAppointmentsServicePublicPageContextValue } from "./CustomAppointmentsServicePublicPageContextValue"

export const CustomAppointmentsServicePublicPage = () => {
    const [selectedDate, setSelectedDate] = useState<LocalDate | null>(null);
    const [freeTimeWindowsOnSelectedDate, setFreeTimeWindowsOnSelectedDate] = useState<[LocalTime, LocalTime][] | null>(null);
    const [selectedTimeWindowStart, setSelectedTimeWindowStart] = useState<LocalTime | null>(null);
    const [selectedTimeWindowEnd, setSelectedTimeWindowEnd] = useState<LocalTime | null>(null);

    const contextValue: CustomAppointmentsServicePublicPageContextValue = {
        selectedDate,
        setSelectedDate,
        freeTimeWindowsOnSelectedDate,
        setFreeTimeWindowsOnSelectedDate,
        selectedTimeWindowStart,
        setSelectedTimeWindowStart,
        selectedTimeWindowEnd,
        setSelectedTimeWindowEnd,
    };

    return <CustomAppointmentsServicePublicPageContext.Provider value={contextValue}>
        <Center height="100vh">
            <StandardPanel width="80%" height="90vh" padding="20px" >
                <StandardFlex>
                    <Text fontSize="3xl">Service</Text>
                    <Text>Enterprise</Text>
                    <CustomAppointmentsServicePublicPageCalendar />
                    <Flex direction="row">
                        <Box flex="1">
                            {freeTimeWindowsOnSelectedDate !== null ?
                                <TimeIntervalsDisplay intervals={freeTimeWindowsOnSelectedDate} resolutionMinutes={30} />
                                : null}
                        </Box>
                        <Box flex="1">
                            <CustomAppointmentsServicePublicPageAppointmentMaker />
                        </Box>
                    </Flex>
                </StandardFlex>
            </StandardPanel>
        </Center >
    </CustomAppointmentsServicePublicPageContext.Provider>
}