import { Box, Center } from "@chakra-ui/react";
import { LocalDateTime } from "@js-joda/core";
import { useEffect, useState } from "react";
import { useServicesApi } from "../../../../api/services-api";
import { StandardPanel } from "../../../../common/StandardPanel";
import { useIntParam } from "../../../../hooks/useIntParam";
import { useNavigateWithToastDismiss } from "../../../../hooks/useNavigateWithToastDismiss";
import { routes } from "../../../../router/routes";
import { errorErrResultAsyncFromPromise } from "../../../../utils/result";
import { ManageNonCustomServicePageContext, ManageNonCustomServicePageContextValue } from "./ManageNonCustomServicePageContext";
import { ManageNonCustomServicePageFutureScheduledAppointment } from "./ManageNonCustomServicePageFutureScheduledAppointment";
import { ManageNonCustomServicePageFutureScheduledAppointmentsList } from "./ManageNonCustomServicePageFutureScheduledAppointmentsList";

export const ManageNonCustomServicePage = () => {
    const navigate = useNavigateWithToastDismiss();
    const serviceId = useIntParam("serviceId");
    const servicesApi = useServicesApi();

    const [futureScheduledAppointments, setFutureScheduledAppointments] = useState<ManageNonCustomServicePageFutureScheduledAppointment[]>([]);

    useEffect(() => {
        async function loadFutureAppointments() {
            const promise = servicesApi.getEnterpriseServiceFutureAppointments(serviceId);
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
            } satisfies ManageNonCustomServicePageFutureScheduledAppointment));
            setFutureScheduledAppointments(appointments);
        }
        loadFutureAppointments();
    }, []);

    const contextValue: ManageNonCustomServicePageContextValue = {
        futureScheduledAppointments,
        setFutureScheduledAppointments
    };

    return <ManageNonCustomServicePageContext.Provider value={contextValue} >
        <Center height="100%" overflowY="scroll">
            <Box width="80%" height="100%">
                <StandardPanel height="100%">
                    <ManageNonCustomServicePageFutureScheduledAppointmentsList />
                </StandardPanel>
            </Box>
        </Center>
    </ManageNonCustomServicePageContext.Provider>;
}