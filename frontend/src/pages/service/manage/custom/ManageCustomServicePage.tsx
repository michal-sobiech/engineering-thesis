import { Box, Center, Flex } from "@chakra-ui/react";
import { LocalDateTime } from "@js-joda/core";
import { useEffect, useState } from "react";
import { useServicesApi } from "../../../../api/services-api";
import { useServiceIdFromLoader } from "../../../../common/loader/service-id-loader";
import { StandardPanel } from "../../../../common/StandardPanel";
import { useNavigateWithToastDismiss } from "../../../../hooks/useNavigateWithToastDismiss";
import { routes } from "../../../../router/routes";
import { errorErrResultAsyncFromPromise } from "../../../../utils/result";
import { ManageCustomServicePageContext, ManageCustomServicePageContextValue } from "./ManageCustomServicePageContext";
import { ManageCustomServicePageFutureScheduledAppointment } from "./ManageCustomServicePageFutureScheduledAppointment";
import { ManageCustomServicePageFutureScheduledAppointmentsList } from "./ManageCustomServicePageFutureScheduledAppointmentsList";
import { ManageCustomServicePagePendingAppointment } from "./ManageCustomServicePagePendingAppointment";
import { ManageCustomServicePagePendingAppointmentsList } from "./ManageCustomServicePagePendingAppointmentsList";

export const ManageCustomServicePage = () => {
    const navigate = useNavigateWithToastDismiss();
    const serviceId = useServiceIdFromLoader();
    const servicesApi = useServicesApi();

    const [futureScheduledAppointments, setFutureScheduledAppointments] = useState<ManageCustomServicePageFutureScheduledAppointment[]>([]);
    const [pendingAppointments, setPendingAppointments] = useState<ManageCustomServicePagePendingAppointment[]>([]);

    useEffect(() => {
        async function loadFutureAppointments() {
            const promise = servicesApi.getEnterpriseServiceUncancelledFutureScheduledAppointments(serviceId);
            const resultAsync = errorErrResultAsyncFromPromise(promise);
            const result = await resultAsync;
            if (result.isErr()) {
                navigate(routes.mainPage);
                return;
            }
            const appointments = result.value.map(row => ({
                appointmentId: row.appointmentId,
                username: row.username,
                userFirstName: row.userFirstName,
                userLastName: row.userLastName,
                address: row.address,
                startDatetimeServiceLocal: LocalDateTime.parse(row.startDatetimeServiceLocal),
                endDatetimeServiceLocal: LocalDateTime.parse(row.endDatetimeServiceLocal),
                timezone: row.timezone,
                price: row.price,
                currency: row.currency,
            } satisfies ManageCustomServicePageFutureScheduledAppointment));
            setFutureScheduledAppointments(appointments);
        }
        loadFutureAppointments();
    }, []);

    useEffect(() => {
        async function loadPendingAppointments() {
            const promise = servicesApi.getEnterpriseServicePendingAppointments(serviceId);
            const resultAsync = errorErrResultAsyncFromPromise(promise);
            const result = await resultAsync;
            if (result.isErr()) {
                navigate(routes.mainPage);
                return;
            }
            const appointments = result.value.map(row => ({
                appointmentId: row.appointmentId,
                username: row.username,
                userFirstName: row.userFirstName,
                userLastName: row.userLastName,
                address: row.address,
                startDatetimeServiceLocal: LocalDateTime.parse(row.startDatetimeServiceLocal),
                endDatetimeServiceLocal: LocalDateTime.parse(row.endDatetimeServiceLocal),
                timezone: row.timezone,
                price: row.price,
                currency: row.currency,
            } satisfies ManageCustomServicePagePendingAppointment));
            setPendingAppointments(appointments);
        }
        loadPendingAppointments();
    }, []);

    const contextValue: ManageCustomServicePageContextValue = {
        futureScheduledAppointments,
        setFutureScheduledAppointments,
        pendingAppointments,
        setPendingAppointments,
    };

    return <ManageCustomServicePageContext.Provider value={contextValue} >
        <Center height="100%" overflowY="scroll">
            <Box width="80%" height="100%">
                <StandardPanel height="100%">
                    <Flex direction="row" height="100%" gap="10px">
                        <Box flex="1">
                            <ManageCustomServicePageFutureScheduledAppointmentsList />
                        </Box>
                        <Box flex="1">
                            <ManageCustomServicePagePendingAppointmentsList />
                        </Box>
                    </Flex>
                </StandardPanel>
            </Box>
        </Center>
    </ManageCustomServicePageContext.Provider>;
}