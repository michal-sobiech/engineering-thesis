import { Box, Center, Flex, Spacer, Text } from "@chakra-ui/react"
import { LocalDate, LocalTime } from "@js-joda/core"
import { useState } from "react"
import { SlotInfo } from "react-big-calendar"
import { useServicesApi } from "../../../../api/services-api"
import { NonEditableCustomMonthlyCalendar } from "../../../../common/calendar/weekly/non-editable/NonEditableCustomMonthlyCalendar"
import { useServiceIdFromLoader } from "../../../../common/loader/service-id-loader"
import { ReportServiceButton } from "../../../../common/report/ReportServiceButton"
import { StandardPanel } from "../../../../common/StandardPanel"
import { TimeIntervalsDisplay } from "../../../../common/TimeIntervalsDisplay"
import { fetchFreeTimeWindowsForCustomAppointmentsOnLocalDate } from "../../../../services/appointments"
import { extractLocalDateFromDate } from "../../../../utils/date"
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../../../../utils/error"
import { Position } from "../../../../utils/Position"
import { toastError } from "../../../../utils/toast"
import { CustomAppointmentsServicePublicPageAppointmentMaker } from "./CustomAppointmentsServicePublicPageAppointmentMaker"
import { CustomAppointmentsServicePublicPageContext, CustomAppointmentsServicePublicPageContextValue } from "./CustomAppointmentsServicePublicPageContextValue"

export const CustomAppointmentsServicePublicPage = () => {
    const serviceId = useServiceIdFromLoader();
    const servicesApi = useServicesApi();

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

    const onSelectSlot = (slot: SlotInfo) => {
        const slotDate = slot.start;
        const slotLocalDate = extractLocalDateFromDate(slotDate);
        setSelectedDate(slotLocalDate);

        try {
            fetchFreeTimeWindowsForCustomAppointmentsOnLocalDate(serviceId, slotLocalDate, servicesApi)
                .then(setFreeTimeWindowsOnSelectedDate);
        } catch (error: unknown) {
            toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
        }
    };

    return <CustomAppointmentsServicePublicPageContext.Provider value={contextValue}>
        <Center height="100%">
            <StandardPanel width="80%" height="100%" padding="20px" overflowY="scroll">
                <Flex direction="column" height="100%" minHeight={0}>

                    <Flex flexShrink={0} direction="row">
                        <Text fontSize="3xl">Service</Text>
                        <Spacer />
                        <ReportServiceButton serviceId={serviceId} />
                    </Flex>

                    <Text flexShrink={0}>Enterprise</Text>

                    <Box flexShrink={0} height="100%">
                        <NonEditableCustomMonthlyCalendar
                            selectedDate={selectedDate}
                            setSelectedDate={setSelectedDate}
                            onSelectSlot={onSelectSlot} />
                    </Box>

                    {freeTimeWindowsOnSelectedDate !== null ?
                        <Flex flex={1} direction="row" height="100%">
                            <Box flex="1" height="100%">
                                <TimeIntervalsDisplay intervals={freeTimeWindowsOnSelectedDate} resolutionMinutes={30} />
                            </Box>
                            <Box flex="1">
                                <CustomAppointmentsServicePublicPageAppointmentMaker />
                            </Box>
                        </Flex>
                        : null}
                </Flex>
            </StandardPanel>
        </Center >
    </CustomAppointmentsServicePublicPageContext.Provider>
}