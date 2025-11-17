import { Box, Center, Flex, Spacer, Text } from "@chakra-ui/react"
import { LocalDate, LocalTime } from "@js-joda/core"
import { useState } from "react"
import { ReportServiceButton } from "../../../../common/report/ReportServiceButton.tsx"
import { StandardFlex } from "../../../../common/StandardFlex"
import { StandardPanel } from "../../../../common/StandardPanel"
import { useIntParam } from "../../../../hooks/useIntParam"
import { NoCustomAppointmentsServicePublicPageCalendar } from "./NoCustomAppointmentsServicePublicPageCalendar"
import { NoCustomAppointmentsServicePublicPageContext, NoCustomAppointmentsServicePublicPageContextValue } from "./NoCustomAppointmentsServicePublicPageContextValue"
import { NoCustomAppointmentsServicePublicPageSlotList } from "./NoCustomAppointmentsServicePublicPageSlotList"
import { NonCustomAppointmentsServicePublicPageAppointmentMaker } from "./NonCustomAppointmentsServicePageAppointmentMaker"

export const NoCustomAppointmentsServicePublicPage = () => {
    const serviceId = useIntParam("serviceId");

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
            <StandardPanel width="80%" height="90%" padding="20%" >
                <StandardFlex>
                    <Flex direction="row">
                        <Text fontSize="3xl">Service</Text>
                        <Spacer />
                        <ReportServiceButton serviceId={serviceId} />
                    </Flex>
                    <Text>Enterprise</Text>
                    <NoCustomAppointmentsServicePublicPageCalendar />
                    <Flex direction="row">
                        <Box flex="1">
                            <NoCustomAppointmentsServicePublicPageSlotList />
                        </Box>
                        <Box flex="1">
                            <NonCustomAppointmentsServicePublicPageAppointmentMaker />
                        </Box>
                    </Flex>
                </StandardFlex>
            </StandardPanel>
        </Center >
    </NoCustomAppointmentsServicePublicPageContext.Provider>
}