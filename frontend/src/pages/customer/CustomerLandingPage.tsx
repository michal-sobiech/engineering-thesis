import { Box, Center, Flex, Heading, Text } from "@chakra-ui/react";
import { format } from "date-fns";
import { enUS } from "date-fns/locale";
import { JSX, useState } from "react";
import { ScrollableList } from "../../common/ScrollableList";
import { StandardButton } from "../../common/StandardButton";
import { StandardConcaveBox } from "../../common/StandardConcaveBox";
import { StandardFlex } from "../../common/StandardFlex";
import { StandardLabeledContainer } from "../../common/StandardLabeledContainer";
import { StandardPanel } from "../../common/StandardPanel";
import { Appointment } from "./Appointment";

export const CustomerLandingPage = () => {
    const [scheduledAppointments, setScheduledAppointments] = useState<Appointment[]>([]);
    // const [pendingCustomAppointments, setPendingCustomAppointments] = useState<>();

    return <Center height="100vh">
        <Box width="80vw" height="100%">
            <StandardPanel>
                <Flex
                    direction="row"
                    gap="10px"
                >
                    <Box flex="1">
                        <StandardLabeledContainer label="Scheduled appointments">
                            <StandardConcaveBox>
                                <ScrollableList>
                                    {createScheduledAppointmentsListItem("aaa", "aaaaaa", new Date(), new Date())}
                                </ScrollableList>
                            </StandardConcaveBox>
                        </StandardLabeledContainer>
                    </Box>

                    <Box flex="1">
                        <StandardLabeledContainer label="Pending custom appointments">
                            <StandardConcaveBox>
                                <ScrollableList>
                                    {/* {createScheduledAppointmentsListItems()} */}
                                </ScrollableList>
                            </StandardConcaveBox>
                        </StandardLabeledContainer>
                    </Box>
                </Flex>
            </StandardPanel>
        </Box>
    </Center>;
}

function createScheduledAppointmentsListItem(serviceName: string, address: string, start: Date, end: Date): JSX.Element {
    const startFormatted = format(start, "dd.MM.yyyy HH:mm", { locale: enUS });
    const endFormatted = format(end, "dd.MM.yyyy HH:mm", { locale: enUS });

    return <StandardPanel>
        <StandardFlex>
            <Heading>
                {serviceName}
            </Heading>
            <Text>
                {"\u{1F4CD}"} {address}
            </Text>
            <Text>
                From {startFormatted} to {endFormatted}
            </Text>
            <StandardButton backgroundColor="primary.darkRed">
                Cancel
            </StandardButton>
        </StandardFlex>
    </StandardPanel>
}