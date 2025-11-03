import { Center, Text } from "@chakra-ui/react"
import { LocalTime } from "js-joda"
import { useState } from "react"
import { StandardFlex } from "../../../../common/StandardFlex"
import { StandardPanel } from "../../../../common/StandardPanel"
import { CustomAppointmentsServicePublicPageAppointmentMaker } from "./CustomAppointmentsServicePublicPageAppointmentMaker"
import { CustomAppointmentsServicePublicPageCalendar } from "./CustomAppointmentsServicePublicPageCalendar"
import { CustomAppointmentsServicePublicPageContext, CustomAppointmentsServicePublicPageContextValue } from "./CustomAppointmentsServicePublicPageContextValue"

export const CustomAppointmentsServicePublicPage = () => {
    const [selectedDate, setSelectedDate] = useState<Date | null>(null);
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
                    <CustomAppointmentsServicePublicPageAppointmentMaker />
                </StandardFlex>
            </StandardPanel>
        </Center >
    </CustomAppointmentsServicePublicPageContext.Provider>
}