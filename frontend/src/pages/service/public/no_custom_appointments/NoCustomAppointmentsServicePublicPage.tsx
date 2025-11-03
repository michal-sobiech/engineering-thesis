import { Center, Text } from "@chakra-ui/react"
import { LocalTime } from "js-joda"
import { useState } from "react"
import { StandardFlex } from "../../../../common/StandardFlex"
import { StandardPanel } from "../../../../common/StandardPanel"
import { NoCustomAppointmentsServicePublicPageCalendar } from "./NoCustomAppointmentsServicePublicPageCalendar"
import { NoCustomAppointmentsServicePublicPageContext, NoCustomAppointmentsServicePublicPageContextValue } from "./NoCustomAppointmentsServicePublicPageContextValue"
import { NoCustomAppointmentsServicePublicPageSlotList } from "./NoCustomAppointmentsServicePublicPageSlotList"

export const NoCustomAppointmentsServicePublicPage = () => {
    const [selectedDate, setSelectedDate] = useState<Date | null>(null);
    const [freeAppointmentsOnSelectedDate, setFreeAppointmentsOnSelectedDate] = useState<[LocalTime, LocalTime][] | null>(null);
    const [selectedAppointment, setSelectedAppointment] = useState<[LocalTime, LocalTime] | null>(null);

    const contextValue: NoCustomAppointmentsServicePublicPageContextValue = {
        selectedDate,
        setSelectedDate,
        freeAppointmentsOnSelectedDate,
        setFreeAppointmentsOnSelectedDate,
        selectedAppointment,
        setSelectedAppointment,
    };

    return <NoCustomAppointmentsServicePublicPageContext.Provider value={contextValue}>
        <Center height="100vh">
            <StandardPanel width="80%" height="90vh" padding="20px" >
                <StandardFlex>
                    <Text fontSize="3xl">Service</Text>
                    <Text>Enterprise</Text>
                    <NoCustomAppointmentsServicePublicPageCalendar />
                    <NoCustomAppointmentsServicePublicPageSlotList />
                </StandardFlex>
            </StandardPanel>
        </Center >
    </NoCustomAppointmentsServicePublicPageContext.Provider>
}