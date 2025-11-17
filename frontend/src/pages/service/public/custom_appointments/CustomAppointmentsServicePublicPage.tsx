import { Box, Center, Flex, Spacer, Text } from "@chakra-ui/react"
import { LocalDate, LocalTime } from "@js-joda/core"
import { useState } from "react"
import { ReportServiceButton } from "../../../../common/report/ReportServiceButton.tsx"
import { StandardFlex } from "../../../../common/StandardFlex"
import { StandardPanel } from "../../../../common/StandardPanel"
import { TimeIntervalsDisplay } from "../../../../common/TimeIntervalsDisplay"
import { useIntParam } from "../../../../hooks/useIntParam"
import { Position } from "../../../../utils/Position"
import { CustomAppointmentsServicePublicPageAppointmentMaker } from "./CustomAppointmentsServicePublicPageAppointmentMaker"
import { CustomAppointmentsServicePublicPageCalendar } from "./CustomAppointmentsServicePublicPageCalendar"
import { CustomAppointmentsServicePublicPageContext, CustomAppointmentsServicePublicPageContextValue } from "./CustomAppointmentsServicePublicPageContextValue"

export const CustomAppointmentsServicePublicPage = () => {
    const serviceId = useIntParam("serviceId");

    const [selectedDate, setSelectedDate] = useState<LocalDate | null>(null);
    const [freeTimeWindowsOnSelectedDate, setFreeTimeWindowsOnSelectedDate] = useState<[LocalTime, LocalTime][] | null>(null);
    const [selectedTimeWindowStart, setSelectedTimeWindowStart] = useState<LocalTime | null>(null);
    const [selectedTimeWindowEnd, setSelectedTimeWindowEnd] = useState<LocalTime | null>(null);
    const [address, setAddress] = useState<string>("");
    const [position, setPosition] = useState<Position | null>(null);

    const contextValue: CustomAppointmentsServicePublicPageContextValue = {
        selectedDate,
        setSelectedDate,
        freeTimeWindowsOnSelectedDate,
        setFreeTimeWindowsOnSelectedDate,
        selectedTimeWindowStart,
        setSelectedTimeWindowStart,
        selectedTimeWindowEnd,
        setSelectedTimeWindowEnd,
        address,
        setAddress,
        position,
        setPosition,
    };

    return <CustomAppointmentsServicePublicPageContext.Provider value={contextValue}>
        <Center height="100%">
            <StandardPanel width="80%" height="90vh" padding="20px" >
                <StandardFlex>
                    <Flex direction="row">
                        <Text fontSize="3xl">Service</Text>
                        <Spacer />
                        <ReportServiceButton serviceId={serviceId} />
                    </Flex>
                    <Text>Enterprise</Text>
                    <CustomAppointmentsServicePublicPageCalendar />
                    {freeTimeWindowsOnSelectedDate !== null ?
                        <Flex direction="row">
                            <Box flex="1" height="30%">
                                <TimeIntervalsDisplay intervals={freeTimeWindowsOnSelectedDate} resolutionMinutes={30} />
                            </Box>
                            <Box flex="1">
                                <CustomAppointmentsServicePublicPageAppointmentMaker />
                            </Box>
                        </Flex>
                        : null}
                </StandardFlex>
            </StandardPanel>
        </Center >
    </CustomAppointmentsServicePublicPageContext.Provider>
}