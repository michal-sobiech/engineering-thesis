import { Box, Center, Flex, Spacer, Text } from "@chakra-ui/react"
import { LocalDate, LocalTime } from "@js-joda/core"
import { useEffect, useState } from "react"
import { SlotInfo } from "react-big-calendar"
import { useNavigate } from "react-router"
import { useServicesApi } from "../../../../api/services-api"
import { NonEditableCustomMonthlyCalendar } from "../../../../common/calendar/weekly/non-editable/NonEditableCustomMonthlyCalendar"
import { LinkText } from "../../../../common/LinkText"
import { useServiceIdFromLoader } from "../../../../common/loader/service-id-loader"
import { ReportServiceButton } from "../../../../common/report/service/ReportEnterpriseServiceButton"
import { StandardConcaveBox } from "../../../../common/StandardConcaveBox"
import { StandardHorizontalSeparator } from "../../../../common/StandardHorizontalSeparator"
import { StandardLabeledContainer } from "../../../../common/StandardLabeledContainer"
import { StandardPanel } from "../../../../common/StandardPanel"
import { TimeIntervalsDisplay } from "../../../../common/TimeIntervalsDisplay"
import { routes } from "../../../../router/routes"
import { fetchFreeTimeWindowsForCustomAppointmentsOnLocalDate } from "../../../../services/appointments"
import { extractLocalDateFromDate } from "../../../../utils/date"
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../../../../utils/error"
import { GeoPosition } from "../../../../utils/GeoPosition"
import { toastError } from "../../../../utils/toast"
import { ServicePublicPageReviewList } from "../ServicePublicPageReviewList"
import { CustomAppointmentsServicePublicPageAppointmentMaker } from "./CustomAppointmentsServicePublicPageAppointmentMaker"
import { CustomAppointmentsServicePublicPageContext, CustomAppointmentsServicePublicPageContextValue } from "./CustomAppointmentsServicePublicPageContextValue"

export const CustomAppointmentsServicePublicPage = () => {
    const serviceId = useServiceIdFromLoader();
    const servicesApi = useServicesApi();
    const navigate = useNavigate();

    const [enterpriseName, setEnterpriseName] = useState<string>("");
    const [enterpriseId, setEnterpriseId] = useState<number | null>(null);
    const [serviceName, setServiceName] = useState<string>("");
    const [description, setDescription] = useState<string>("");
    const [selectedDate, setSelectedDate] = useState<LocalDate | null>(null);
    const [freeTimeWindowsOnSelectedDate, setFreeTimeWindowsOnSelectedDate] = useState<[LocalTime, LocalTime][] | null>(null);
    const [selectedTimeWindowStart, setSelectedTimeWindowStart] = useState<LocalTime | null>(null);
    const [selectedTimeWindowEnd, setSelectedTimeWindowEnd] = useState<LocalTime | null>(null);
    const [address, setAddress] = useState<string>("");
    const [position, setPosition] = useState<GeoPosition | null>(null);

    useEffect(() => {
        servicesApi.getCustomEnterpriseService(serviceId)
            .then(response => {
                setEnterpriseName(response.enterpriseName);
                setEnterpriseId(response.enterpriseId);
                setServiceName(response.name);
                setDescription(response.description);
            }).catch(() => {
                toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
                navigate(routes.mainPage);
            })
    }, []);

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

    let enterpriseLabel;
    console.log(enterpriseId);
    if (enterpriseId === null) {
        enterpriseLabel = <Text>{enterpriseName}</Text>;
    } else {
        enterpriseLabel = <LinkText url={routes.enterprisePublic(enterpriseId)}>
            {enterpriseName}
        </LinkText>;
    }

    return <CustomAppointmentsServicePublicPageContext.Provider value={contextValue}>
        <Center height="100%">
            <StandardPanel width="80%" height="100%" padding="20px" overflowY="scroll">
                <Flex direction="column" height="100%" minHeight={0} gap="10px">

                    <Flex flexShrink={0} direction="row">
                        <Text fontSize="3xl">{serviceName}</Text>
                        <Spacer />
                        <ReportServiceButton serviceId={serviceId} />
                    </Flex>

                    {enterpriseLabel}

                    <Text>{description}</Text>

                    <StandardHorizontalSeparator />

                    <StandardLabeledContainer flexGrow={1} maxHeight="30%" label="Reviews">
                        <StandardConcaveBox height="100%" padding="5px">
                            <ServicePublicPageReviewList height="100%" serviceId={serviceId} />
                        </StandardConcaveBox>
                    </StandardLabeledContainer>

                    <StandardHorizontalSeparator />

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