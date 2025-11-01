import { Center, Text } from "@chakra-ui/react"
import { useState } from "react"
import { StandardFlex } from "../../../common/StandardFlex"
import { StandardPanel } from "../../../common/StandardPanel"
import { ServicePublicPageContext, ServicePublicPageContextValue } from "./ServicePublicPageContext"
import { ServicePublicPageNoCustomAppointmentsCalendar } from "./ServicePublicPageNoCustomAppointmentsCalendar"
import { ServicePublicPageNoCustomAppointmentsSlotList } from "./ServicePublicPageNoCustomAppointmentsSlotList"

export const NoCustomAppointmentsServicePublicPage = () => {
    const [selectedDate, setSelectedDate] = useState<Date | null>(null);
    const [freeAppointmentsOnSelectedDate, setFreeAppointmentsOnSelectedDate] = useState<[Date, Date][] | null>(null);
    const [selectedAppointment, setSelectedAppointment] = useState<[Date, Date] | null>(null);

    const contextValue: ServicePublicPageContextValue = {
        selectedDate,
        setSelectedDate,
        freeAppointmentsOnSelectedDate,
        setFreeAppointmentsOnSelectedDate,
        selectedAppointment,
        setSelectedAppointment,
    };

    return <ServicePublicPageContext.Provider value={contextValue}>
        <Center height="100vh">
            <StandardPanel width="80%" height="90vh" padding="20px" >
                <StandardFlex>
                    <Text fontSize="3xl">Service</Text>
                    <Text>Enterprise</Text>
                    <ServicePublicPageNoCustomAppointmentsCalendar />
                    <ServicePublicPageNoCustomAppointmentsSlotList />
                </StandardFlex>
            </StandardPanel>
        </Center >
    </ServicePublicPageContext.Provider>
}