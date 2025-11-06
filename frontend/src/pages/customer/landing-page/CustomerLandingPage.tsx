import { Box, Center, Flex } from "@chakra-ui/react";
import { useState } from "react";
import { StandardPanel } from "../../../common/StandardPanel";
import { CustomerLandingPagePendingAppointmentsList } from "./CustomerLandingPagePendingAppointmentsList";
import { CustomerLandingPageScheduledAppointment } from "./CustomerLandingPageScheduledAppointment";
import { CustomerLandingPageScheduledAppointmentsList } from "./CustomerLandingPageScheduledAppointmentsList";

export const CustomerLandingPage = () => {
    const [scheduledAppointments, setScheduledAppointments] = useState<CustomerLandingPageScheduledAppointment[]>([]);
    // const [pendingCustomAppointments, setPendingCustomAppointments] = useState<>();

    return <Center height="100vh">
        <Box width="80vw" height="100%">
            <StandardPanel>
                <Flex direction="row" gap="50px">
                    <Box flex="1">
                        <CustomerLandingPageScheduledAppointmentsList />
                    </Box>
                    <Box flex="1">
                        <CustomerLandingPagePendingAppointmentsList />
                    </Box>
                </Flex>
            </StandardPanel>
        </Box>
    </Center>;
}
