import { Box, Center, Heading } from "@chakra-ui/react"
import { useState } from "react"
import { servicesApi } from "../../../api/services-api"
import { LocationAutocomplete } from "../../../common/LocationAutocomplete"
import { StandardButton } from "../../../common/StandardButton"
import { StandardDateRangePicker } from "../../../common/StandardDateRangePicker"
import { StandardFlex } from "../../../common/StandardFlex"
import { StandardHorizontalSeparator } from "../../../common/StandardHorizontalSeparator"
import { StandardLabeledContainer } from "../../../common/StandardLabeledContainer"
import { StandardPanel } from "../../../common/StandardPanel"
import { StandardTextField } from "../../../common/StandardTextField"
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../../../utils/error"
import { Position } from "../../../utils/Position"
import { errorErrResultAsyncFromPromise } from "../../../utils/result"
import { toastError } from "../../../utils/toast"
import { ServiceCathegory } from "../ServiceCathegory"
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
    const [startDate, setStartDate] = useState<Date | null>(null);
    const [endDate, setEndDate] = useState<Date | null>(null);

    const onSearchClick = async () => {
        if (address === "" || position === null) {
            toastError("Enter your location");
            return;
        }

        if (serviceCathegory === null) {
            toastError("Choose a cathegory");
            return;
        }

        if (serviceCathegory === null) {
            toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
            return;
        }

        if (startDate === null) {
            toastError("Choose first time boundary");
            return
        }

        if (endDate === null) {
            toastError("Choose second time boundary");
            return;
        }

        const longitude = position.x;
        const latitude = position.y
        const promise = servicesApi.searchServices(
            longitude,
            latitude,
            serviceCathegory,
            MAX_DISTANCE_KM,
            serviceName,
            enterpriseName,
            startDate,
            endDate
        );
        const result = await errorErrResultAsyncFromPromise(promise);
        if (result.isErr()) {
            toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
            return;
        } else {
            setServices(result.value);
        }
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
                    <StandardLabeledContainer label="When?">
                        <StandardDateRangePicker date1={startDate} setDate1={setStartDate} date2={endDate} setDate2={setEndDate} />
                    </StandardLabeledContainer>
                    <StandardLabeledContainer label="Preferred location">
                        <LocationAutocomplete address={address} setAddress={setAddress} position={position} setPosition={setPosition} />
                    </StandardLabeledContainer>
                    <StandardButton onClick={onSearchClick}>
                        Search
                    </StandardButton>
                    <StandardHorizontalSeparator />
                    <ServiceSearchServicesList services={services} />
                </StandardFlex>
            </StandardPanel>
        </Box>
    </Center >
}

