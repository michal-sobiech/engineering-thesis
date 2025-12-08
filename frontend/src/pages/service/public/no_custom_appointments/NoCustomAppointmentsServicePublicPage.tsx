import { Box, Center, Flex, Spacer, Text } from "@chakra-ui/react"
import { LocalDate, LocalTime } from "@js-joda/core"
import { useState } from "react"
import { useServiceIdFromLoader } from "../../../../common/loader/service-id-loader"
import { ReportServiceButton } from "../../../../common/report/ReportServiceButton"
import { StandardPanel } from "../../../../common/StandardPanel"
import { NoCustomAppointmentsServicePublicPageCalendar } from "./NoCustomAppointmentsServicePublicPageCalendar"
import { NoCustomAppointmentsServicePublicPageContext, NoCustomAppointmentsServicePublicPageContextValue } from "./NoCustomAppointmentsServicePublicPageContextValue"
import { NoCustomAppointmentsServicePublicPageSlotList } from "./NoCustomAppointmentsServicePublicPageSlotList"
import { NonCustomAppointmentsServicePublicPageAppointmentMaker } from "./NonCustomAppointmentsServicePageAppointmentMaker"

export const NoCustomAppointmentsServicePublicPage = () => {
    const serviceId = useServiceIdFromLoader();

    const [selectedDate, setSelectedDate] = useState<LocalDate | null>(null);
    const [freeSlotsOnSelectedDate, setFreeSlotsOnSelectedDate] = useState<[LocalTime, LocalTime][] | null>(null);
    const [selectedSlot, setSelectedSlot] = useState<[LocalTime, LocalTime] | null>(null);

    const contextValue: NoCustomAppointmentsServicePublicPageContextValue = {
        selectedDate,
        setSelectedDate,
        freeSlotsOnSelectedDate,
        setFreeSlotsOnSelectedDate,
        selectedSlot,
        setSelectedSlot,
    };

    return <NoCustomAppointmentsServicePublicPageContext.Provider value={contextValue}>
        <Center height="100%">
            <StandardPanel width="80%" height="100%" padding="2%" >
                <Flex height="100%" direction="column">
                    <Flex direction="row">
                        <Text fontSize="3xl">Service</Text>
                        <Spacer />
                        <ReportServiceButton serviceId={serviceId} />
                    </Flex>
                    <Text>Enterpriseeeee</Text>
                    <Box height="100%">
                        <NoCustomAppointmentsServicePublicPageCalendar />
                    </Box>
                    <Flex direction="row" height="100%">
                        <Box flex="1">
                            <NoCustomAppointmentsServicePublicPageSlotList />
                        </Box>
                        <Box flex="1">
                            <NonCustomAppointmentsServicePublicPageAppointmentMaker />
                        </Box>
                    </Flex>
                </Flex>
            </StandardPanel>
        </Center >
    </NoCustomAppointmentsServicePublicPageContext.Provider>
}