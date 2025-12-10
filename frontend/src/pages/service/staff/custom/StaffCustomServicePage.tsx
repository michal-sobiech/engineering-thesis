import { Box, Center, Flex, Spacer, Text } from "@chakra-ui/react"
import { useState } from "react"
import { useServiceIdFromLoader } from "../../../../common/loader/service-id-loader"
import { MapLocationPicker } from "../../../../common/MapLocationPicker"
import { ReportServiceButton } from "../../../../common/report/ReportServiceButton"
import { StandardLabeledContainer } from "../../../../common/StandardLabeledContainer"
import { StandardPanel } from "../../../../common/StandardPanel"
import { StandardTextField } from "../../../../common/StandardTextField"
import { StandardTimeZonePicker } from "../../../../common/StandardTimeZonePicker"
import { TimeIntervalsDisplay } from "../../../../common/TimeIntervalsDisplay"
import { GeoPosition } from "../../../../utils/GeoPosition"
import { CustomEvents } from "../../calendar/Events"
import { ServiceCathegory } from "../../ServiceCathegory"
import { StaffCustomServicePageContext, StaffCustomServicePageContextValue } from "./StaffCustomServicePageContext"
import { EventWithId } from "../../../../common/calendar/EventWithId"

export const StaffCustomServicePage = () => {
    const serviceId = useServiceIdFromLoader();

    const [enterpriseName, setEnterpriseName] = useState<string>("");
    const [serviceName, setServiceName] = useState<string>("");
    const [serviceDescription, setServiceDescription] = useState<string>("");
    const [address, setAddress] = useState<string | null>(null);
    const [position, setPosition] = useState<GeoPosition | null>(null);
    const [timezone, setTimezone] = useState<string | null>(null);
    const [eventsData, setEventsData] = useState<EventWithId[]>([]);
    const [appointmentDurationMinutes, setAppointmentDurationMinutes] = useState<number | null>(30);
    const [price, setPrice] = useState<number | null>(null);
    const [cathegory, setCathegory] = useState<ServiceCathegory | null>(null);

    const contextValue: StaffCustomServicePageContextValue = {
        enterpriseName, setEnterpriseName,
        serviceName, setServiceName,
        serviceDescription, setServiceDescription,
        address, setAddress,
        position, setPosition,
        timezone, setTimezone,
        eventsData, setEventsData,
        appointmentDurationMinutes, setAppointmentDurationMinutes,
        price, setPrice,
        cathegory, setCathegory,
    };

    return <StaffCustomServicePageContext.Provider value={contextValue}>
        <Center height="100%">
            <StandardPanel width="80%" height="100%" padding="20px" overflowY="scroll">
                <Flex direction="column" height="100%" minHeight={0}>

                    <StandardLabeledContainer label="Enterprise name">
                        <Text>{enterpriseName}</Text>
                    </StandardLabeledContainer>

                    <StandardLabeledContainer label="Service name">
                        <StandardTextField text={serviceName} setText={setServiceName} />
                    </StandardLabeledContainer>

                    <StandardLabeledContainer label="Service description">
                        <StandardTextField text={serviceDescription} setText={setServiceDescription} />
                    </StandardLabeledContainer>

                    <StandardLabeledContainer label="Location">
                        <MapLocationPicker
                            address={address}
                            setAddress={setAddress}
                            position={position}
                            setPosition={setPosition}
                        />
                    </StandardLabeledContainer>

                    <StandardLabeledContainer label="Timezone">
                        <StandardTimeZonePicker value={timezone} setValue={setTimezone} />
                    </StandardLabeledContainer>

                    <

                </Flex>
            </StandardPanel>
        </Center >
    </StaffCustomServicePageContext.Provider>

}