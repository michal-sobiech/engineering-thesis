import { Box, Center, Heading } from "@chakra-ui/react"
import { useEffect, useState } from "react"
import { useSearchParams } from "react-router-dom"
import { useServicesApi } from "../../../api/services-api"
import { LocationAutocomplete } from "../../../common/LocationAutocomplete"
import { StandardButton } from "../../../common/StandardButton"
import { StandardDateRangePicker } from "../../../common/StandardDateRangePicker"
import { StandardFlex } from "../../../common/StandardFlex"
import { StandardHorizontalSeparator } from "../../../common/StandardHorizontalSeparator"
import { StandardLabeledContainer } from "../../../common/StandardLabeledContainer"
import { StandardPanel } from "../../../common/StandardPanel"
import { StandardTextField } from "../../../common/StandardTextField"
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../../../utils/error"
import { GeoPosition } from "../../../utils/GeoPosition"
import { errorErrResultAsyncFromPromise } from "../../../utils/result"
import { toastError } from "../../../utils/toast"
import { ServiceCathegory } from "../ServiceCathegory"
import { ServiceCathegoryPicker } from "../ServiceCathegoryPicker"
import { ServiceSearchServiceData } from "./ServiceSearchServiceData"
import { ServiceSearchServicesList } from "./ServiceSearchServicesList"

const MAX_DISTANCE_KM = 50;

const urlParams = {
    serviceName: "serviceName",
    enterpriseName: "enterpriseName",
    serviceCathegory: "serviceCathegory",
    address: "address",
    positionX: "positionX",
    positionY: "positionY",
    startDate: "startDate",
    endDate: "endDate",
} as const;

export const ServiceSearchPage = () => {
    const [searchParams, setSearchParams] = useSearchParams();

    const servicesApi = useServicesApi();

    const [serviceName, setServiceName] = useState<string>(searchParams.get(urlParams.serviceName) ?? "");
    const [enterpriseName, setEnterpriseName] = useState<string>(searchParams.get(urlParams.enterpriseName) ?? "");
    const [serviceCathegory, setServiceCathegory] = useState<ServiceCathegory | null>(searchParams.get(urlParams.serviceCathegory)
        ? searchParams.get(urlParams.serviceCathegory) as ServiceCathegory
        : null);
    const [address, setAddress] = useState<string>(searchParams.get(urlParams.address) ?? "");
    const [positionX, setPositionX] = useState<number | null>(searchParams.get(urlParams.positionX)
        ? Number(searchParams.get(urlParams.positionX))
        : null);
    const [positionY, setPositionY] = useState<number | null>(searchParams.get(urlParams.positionY)
        ? Number(searchParams.get(urlParams.positionY))
        : null);
    const [startDate, setStartDate] = useState<Date | null>(searchParams.get(urlParams.startDate)
        ? new Date(searchParams.get(urlParams.startDate)!)
        : null);
    const [endDate, setEndDate] = useState<Date | null>(searchParams.get(urlParams.endDate)
        ? new Date(searchParams.get(urlParams.endDate)!)
        : null);

    const [services, setServices] = useState<ServiceSearchServiceData[]>([]);

    useEffect(() => {
        setSearchParams({
            serviceName,
            enterpriseName,
            serviceCathegory: serviceCathegory ?? "",
            address,
            positionX: positionX?.toString() ?? "",
            positionY: positionY?.toString() ?? "",
            startDate: startDate?.toISOString() ?? "",
            endDate: endDate?.toISOString() ?? "",
        } satisfies Record<keyof typeof urlParams, unknown>,
            { replace: true });
    }, [
        serviceName,
        enterpriseName,
        serviceCathegory,
        address,
        positionX,
        positionY,
        startDate,
        endDate
    ])

    const onSearchClick = async () => {
        if (address === "" || positionX === null || positionY === null) {
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

        if (startDate < new Date()) {
            toastError("Choose a future time range");
            return;
        }

        if (endDate === null) {
            toastError("Choose second time boundary");
            return;
        }

        const longitude = positionX;
        const latitude = positionY;
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
        console.log("aaaaaaaaaaaa")
        if (result.isErr()) {
            toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
            return;
        } else {
            setServices(result.value);
        }
    }

    const position: GeoPosition | null = positionX !== null && positionY !== null
        ? { x: positionX, y: positionY }
        : null;
    const setPosition = (value: GeoPosition | null) => {
        if (value !== null) {
            setPositionX(value.x);
            setPositionY(value.y);
        }
    }

    return <Center height="100%" overflowY="scroll">
        <Box width="80%" height="100%">
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

