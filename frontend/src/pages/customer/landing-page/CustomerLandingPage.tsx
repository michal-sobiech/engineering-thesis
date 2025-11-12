import { Box, Center, Flex } from "@chakra-ui/react";
import { LocalDateTime } from "@js-joda/core";
import { useEffect, useState } from "react";
import { useAppointmentsApi } from "../../../api/appointments-api";
import { StandardPanel } from "../../../common/StandardPanel";
import { errorErrResultAsyncFromPromise } from "../../../utils/result";
import { CustomerLandingPageContext, CustomerLandingPageContextValue } from "./CustomerLandingPageContext";
import { CustomerLandingPageFutureScheduledAppointmentsList } from "./CustomerLandingPageFutureScheduledAppointmentsList";
import { CustomerLandingPagePastScheduledAppointments } from "./CustomerLandingPagePastScheduledAppointments";
import { CustomerLandingPagePendingAppointment } from "./CustomerLandingPagePendingAppointment";
import { CustomerLandingPagePendingAppointmentsList } from "./CustomerLandingPagePendingAppointmentsList";
import { CustomerLandingPageRejectedAppointment } from "./CustomerLandingPageRejectedAppointment";
import { CustomerLandingPageScheduledAppointment } from "./CustomerLandingPageScheduledAppointment";

export const CustomerLandingPage = () => {
    const appointmentsApi = useAppointmentsApi();

    const [futureScheduledAppointments, setFutureScheduledAppointments] = useState<CustomerLandingPageScheduledAppointment[]>([]);
    const [pastScheduledAppointments, setPastScheduledAppointments] = useState<CustomerLandingPageScheduledAppointment[]>([]);
    const [pendingAppointments, setPendingAppointments] = useState<CustomerLandingPagePendingAppointment[]>([]);
    const [rejectedAppointments, setRejectedAppointments] = useState<CustomerLandingPageRejectedAppointment[]>([]);

    const contextValue: CustomerLandingPageContextValue = {
        futureScheduledAppointments, setFutureScheduledAppointments,
        pastScheduledAppointments, setPastScheduledAppointments,
        pendingAppointments, setPendingAppointments,
        rejectedAppointments, setRejectedAppointments
    };

    useEffect(() => {
        async function loadFutureScheduledAppointments() {
            const promise = appointmentsApi.getMyFutureScheduledAppointments();
            const asyncResult = errorErrResultAsyncFromPromise(promise);
            const result = await asyncResult;
            if (result.isErr()) {
                return;
            }
            const mapped: CustomerLandingPageScheduledAppointment[] = result.value.map(item => ({
                serviceName: item.serviceName,
                enterpriseName: item.enterpriseName,
                address: item.address,
                startDatetimeServiceLocal: LocalDateTime.parse(item.startDatetimeServiceLocal),
                endDatetimeServiceLocal: LocalDateTime.parse(item.endDatetimeServiceLocal),
                timezone: item.timezone,
                price: item.price
            }));
            setFutureScheduledAppointments(mapped);
        }
        loadFutureScheduledAppointments();
    }, []);

    useEffect(() => {
        async function loadPastScheduledAppointments() {
            const promise = appointmentsApi.getMyPastScheduledAppointments();
            const asyncResult = errorErrResultAsyncFromPromise(promise);
            const result = await asyncResult;
            if (result.isErr()) {
                return;
            }
            const mapped: CustomerLandingPageScheduledAppointment[] = result.value.map(item => ({
                serviceName: item.serviceName,
                enterpriseName: item.enterpriseName,
                address: item.address,
                startDatetimeServiceLocal: LocalDateTime.parse(item.startDatetimeServiceLocal),
                endDatetimeServiceLocal: LocalDateTime.parse(item.endDatetimeServiceLocal),
                timezone: item.timezone,
                price: item.price
            }));
            setPastScheduledAppointments(mapped);
        }
        loadPastScheduledAppointments();
    }, []);

    useEffect(() => {
        async function loadPendingAppointments() {
            const promise = appointmentsApi.getMyPendingAppointments();
            const asyncResult = errorErrResultAsyncFromPromise(promise);
            const result = await asyncResult;
            if (result.isErr()) {
                return;
            }
            const mapped: CustomerLandingPageScheduledAppointment[] = result.value.map(item => ({
                serviceName: item.serviceName,
                enterpriseName: item.enterpriseName,
                address: item.address,
                startDatetimeServiceLocal: LocalDateTime.parse(item.startDatetimeServiceLocal),
                endDatetimeServiceLocal: LocalDateTime.parse(item.endDatetimeServiceLocal),
                timezone: item.timezone,
                price: item.price
            }));
            setPendingAppointments(mapped);
        }
        loadPendingAppointments();
    }, []);

    useEffect(() => {
        async function loadRejectedAppointments() {
            const promise = appointmentsApi.getMyRejectedAppointments();
            const asyncResult = errorErrResultAsyncFromPromise(promise);
            const result = await asyncResult;
            if (result.isErr()) {
                return;
            }
            const mapped: CustomerLandingPageRejectedAppointment[] = result.value.map(item => ({
                serviceName: item.serviceName,
                enterpriseName: item.enterpriseName,
                address: item.address,
                startDatetimeServiceLocal: LocalDateTime.parse(item.startDatetimeServiceLocal),
                endDatetimeServiceLocal: LocalDateTime.parse(item.endDatetimeServiceLocal),
                timezone: item.timezone,
                price: item.price,
                rejectionMessage: item.rejectionMessage,
            }));
            setRejectedAppointments(mapped);
        }
        loadRejectedAppointments();
    }, []);

    const futureScheduledAppointmentsList = futureScheduledAppointments.length === 0
        ? null
        : <Box flex="1">
            <CustomerLandingPageFutureScheduledAppointmentsList />
        </Box>;

    const pastScheduledAppointmentsList = pastScheduledAppointments.length === 0
        ? null
        : <Box flex="1">
            <CustomerLandingPagePastScheduledAppointments />
        </Box>;

    const pendingAppointmentsList = pendingAppointments.length === 0
        ? null
        : <Box flex="1">
            <CustomerLandingPagePendingAppointmentsList />
        </Box>;

    const rejectedAppointmentsList = rejectedAppointments.length === 0
        ? null
        : <Box flex="1">

        </Box>;

    return <CustomerLandingPageContext.Provider value={contextValue}>
        <Center height="100vh">
            <Box width="80vw" height="100%">
                <StandardPanel>
                    <Flex direction="row" gap="50px">
                        {futureScheduledAppointmentsList}
                        {pendingAppointmentsList}
                        {rejectedAppointmentsList}
                        {pastScheduledAppointmentsList}
                    </Flex>
                </StandardPanel>
            </Box>
        </Center>
    </CustomerLandingPageContext.Provider>;
}
