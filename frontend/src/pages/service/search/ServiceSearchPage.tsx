import { Box, Center, Heading } from "@chakra-ui/react"
import { useState } from "react"
import { servicesApi } from "../../../api/services-api"
import { LocationAutocomplete } from "../../../common/LocationAutocomplete"
import { StandardFlex } from "../../../common/StandardFlex"
import { StandardHorizontalSeparator } from "../../../common/StandardHorizontalSeparator"
import { StandardLabeledContainer } from "../../../common/StandardLabeledContainer"
import { StandardPanel } from "../../../common/StandardPanel"
import { StandardTextField } from "../../../common/StandardTextField"
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../../../utils/error"
import { Position } from "../../../utils/Position"
import { toastError } from "../../../utils/toast"
import { ServiceCathegory, serviceCathegoryLabels } from "../ServiceCathegory"
import { ServiceCathegoryPicker } from "../ServiceCathegoryPicker"
import { ServiceSearchServiceData } from "./ServiceSearchServiceData"
import { ServiceSearchServicesList } from "./ServiceSearchServicesList"

const MAX_DISTANCE_KM = 50;

export const ServiceSearchPage = () => {
    const [serviceName, setServiceName] = useState<string>("");
    const [enterpriseName, setEnterpriseName] = useState<string>("");
    const [serviceCathegory, setServiceCathegory] = useState<ServiceCathegory | null>(null);
    const [address, setAddress] = useState<string>("");
    const [position, setPosition] = useState<Position | null>(null);
    const [services, setServices] = useState<ServiceSearchServiceData[]>([]);

    const serviceCathegoryLabel = serviceCathegory === null
        ? "Choose a cathegory"
        : serviceCathegoryLabels[serviceCathegory];

    const onSearchClick = async () => {
        if (address === "" || position === null) {
            toastError("Enter your location");
            return;
        }

        if (serviceCathegory === null) {
            toastError("Choose a cathegory");
            return;
        }

        const cathegory: string | null = serviceCathegoryLabels[serviceCathegory];
        if (cathegory === null) {
            toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
            return;
        }

        const longitude = position.x;
        const latitude = position.y
        const promise = await servicesApi.searchServices(
            longitude,
            latitude,
            cathegory,
            MAX_DISTANCE_KM,
            serviceName,
            enterpriseName,

        );
    }

    return <Center height="100vh">
        <Box width="80vw" height="100%">
            <StandardPanel>
                <StandardFlex>
                    <Heading>
                        Find a service
                    </Heading>
                    <StandardLabeledContainer label="Service name">
                        <StandardTextField text={serviceName} setText={setServiceName} placeholder="Female haircut" />
                    </StandardLabeledContainer>
                    <StandardLabeledContainer label="Enterprise name">
                        <StandardTextField text={enterpriseName} setText={setEnterpriseName} placeholder="The Best Barbershop" />
                    </StandardLabeledContainer>
                    <StandardLabeledContainer label="Service cathegory">
                        <ServiceCathegoryPicker value={serviceCathegory} setValue={setServiceCathegory} />
                    </StandardLabeledContainer>
                    <StandardLabeledContainer label="Preferred location">
                        <LocationAutocomplete query={address} setQuery={setAddress} />
                    </StandardLabeledContainer>
                    <StandardHorizontalSeparator />
                    <ServiceSearchServicesList services={services} />
                </StandardFlex>
            </StandardPanel>
        </Box>
    </Center >
}

