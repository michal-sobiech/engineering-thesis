import { Box, Center, Flex } from "@chakra-ui/react"
import { LocationAutocomplete } from "../../../../common/LocationAutocomplete"
import { TimeRangePicker } from "../../../../common/TimeRangePicker"
import { useContextOrThrow } from "../../../../hooks/useContextOrThrow"
import { CustomAppointmentsServicePublicPageAppointmentMakerButton } from "./CustomAppointmentsServicePublicPageAppointmentMakerButton"
import { CustomAppointmentsServicePublicPageContext } from "./CustomAppointmentsServicePublicPageContextValue"

export const CustomAppointmentsServicePublicPageAppointmentMaker = () => {
    const {
        selectedTimeWindowStart,
        setSelectedTimeWindowStart,
        selectedTimeWindowEnd,
        setSelectedTimeWindowEnd,
        position,
        setPosition,
        address,
        setAddress,
    } = useContextOrThrow(CustomAppointmentsServicePublicPageContext);

    return <Center height="100%">
        <Flex direction="column" gap="5px">
            <Box flex="1">
                <TimeRangePicker
                    time1={selectedTimeWindowStart}
                    setTime1={setSelectedTimeWindowStart}
                    time2={selectedTimeWindowEnd}
                    setTime2={setSelectedTimeWindowEnd} />
            </Box>
            <Box flex="1">
                <LocationAutocomplete
                    position={position}
                    setPosition={setPosition}
                    address={address}
                    setAddress={setAddress} />
            </Box>
            <Box flex="1">
                <CustomAppointmentsServicePublicPageAppointmentMakerButton />
            </Box>
        </Flex>
    </Center>
}