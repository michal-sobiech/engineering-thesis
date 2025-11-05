import { Center, Flex } from "@chakra-ui/react"
import { TimeRangePicker } from "../../../../common/TimeRangePicker"
import { useContextOrThrow } from "../../../../utils/useContextOrThrow"
import { CustomAppointmentsServicePublicPageAppointmentMakerButton } from "./CustomAppointmentsServicePublicPageAppointmentMakerButton"
import { CustomAppointmentsServicePublicPageContext } from "./CustomAppointmentsServicePublicPageContextValue"

export const CustomAppointmentsServicePublicPageAppointmentMaker = () => {
    const {
        selectedTimeWindowStart,
        setSelectedTimeWindowStart,
        selectedTimeWindowEnd,
        setSelectedTimeWindowEnd,
    } = useContextOrThrow(CustomAppointmentsServicePublicPageContext);

    return <Center height="100%">
        <Flex direction="column">
            <TimeRangePicker
                time1={selectedTimeWindowStart}
                setTime1={setSelectedTimeWindowStart}
                time2={selectedTimeWindowEnd}
                setTime2={setSelectedTimeWindowEnd} />
            <CustomAppointmentsServicePublicPageAppointmentMakerButton />
        </Flex>
    </Center>
}