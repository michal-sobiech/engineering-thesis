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
import { routes } from "../../../../router/routes"
import { fetchFreeAppointmentsOnDateInServiceTimezone } from "../../../../services/appointments"
import { extractLocalDateFromDate } from "../../../../utils/date"
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../../../../utils/error"
import { toastError } from "../../../../utils/toast"
import { ServicePublicPageReviewList } from "../ServicePublicPageReviewList"
import { NoCustomAppointmentsServicePublicPageContext, NoCustomAppointmentsServicePublicPageContextValue } from "./NoCustomAppointmentsServicePublicPageContextValue"
import { NoCustomAppointmentsServicePublicPageSlotList } from "./NoCustomAppointmentsServicePublicPageSlotList"
import { NonCustomAppointmentsServicePublicPageAppointmentMaker } from "./NonCustomAppointmentsServicePageAppointmentMaker"

export const NoCustomAppointmentsServicePublicPage = () => {
    const serviceId = useServiceIdFromLoader();
    const servicesApi = useServicesApi();
    const navigate = useNavigate();

    const [enterpriseName, setEnterpriseName] = useState<string>("");
    const [enterpriseId, setEnterpriseId] = useState<number | null>(null);
    const [serviceName, setServiceName] = useState<string>("");
    const [description, setDescription] = useState<string>("");
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

    console.log(freeSlotsOnSelectedDate)

    useEffect(() => {
        servicesApi.getNonCustomEnterpriseService(serviceId)
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

    const onSelectSlot = (slot: SlotInfo) => {
        const slotDate = slot.start;
        const slotLocalDate = extractLocalDateFromDate(slotDate);
        setSelectedDate(slotLocalDate);

        try {
            fetchFreeAppointmentsOnDateInServiceTimezone(serviceId, slotLocalDate, servicesApi)
                .then(setFreeSlotsOnSelectedDate);
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

    return <NoCustomAppointmentsServicePublicPageContext.Provider value={contextValue}>
        <Center height="100%">
            <StandardPanel width="80%" height="100%" padding="2%" >
                <Flex height="100%" direction="column" gap="10px">
                    <Flex direction="row">
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

                    <Box height="100%">
                        <NonEditableCustomMonthlyCalendar
                            selectedDate={selectedDate}
                            setSelectedDate={setSelectedDate}
                            onSelectSlot={onSelectSlot} />
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
    </NoCustomAppointmentsServicePublicPageContext.Provider >
}