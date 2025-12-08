import { Box, Center, Text } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import { Location } from "../../GENERATED-api";
import { useEnterprisesApi } from "../../api/enterprises-api";
import { useServicesApi } from "../../api/services-api";
import { MapLocationPicker } from "../../common/MapLocationPicker";
import { StandardButton } from "../../common/StandardButton";
import { StandardFlex } from "../../common/StandardFlex";
import { StandardFloatInput } from "../../common/StandardFloatInput";
import { StandardLabeledContainer } from "../../common/StandardLabeledContainer";
import { StandardPanel } from "../../common/StandardPanel";
import { StandardTextField } from "../../common/StandardTextField";
import { StandardTimeZonePicker } from "../../common/StandardTimeZonePicker";
import { useEnterpriseIdFromLoader } from "../../common/loader/enterprise-id-loader";
import { routes } from "../../router/routes";
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../../utils/error";
import { combine, errorErrResultAsyncFromPromise } from "../../utils/result";
import { eventWithIdAndCapacityToSlot } from "../../utils/slot";
import { eventWithIdToTimeWindow } from "../../utils/time-window";
import { toastError } from "../../utils/toast";
import { ServiceCathegory } from "./ServiceCathegory";
import { ServiceCathegoryPicker } from "./ServiceCathegoryPicker";
import { ServiceCreationCalendar } from "./ServiceCreationCalendar";
import { CustomOrNotAppointmentsEvents } from "./calendar/CustomAppointmentsEvents";

export const ServiceCreationPage = () => {
    const MAX_DISANCE_KM = 50;

    const enterprisesApi = useEnterprisesApi();
    const servicesApi = useServicesApi();
    const navigate = useNavigate();
    const enterpriseId = useEnterpriseIdFromLoader();

    const [enterpriseName, setEnterpriseName] = useState<string>("");
    const [serviceName, setServiceName] = useState<string>("");
    const [serviceDescription, setServiceDescription] = useState<string>("");
    const [address, setAddress] = useState<string | null>(null);
    const [position, setPosition] = useState<{ latitude: number; longitude: number } | null>(null);
    const [timeZone, setTimeZone] = useState<string | null>(null);
    const [eventsData, setEventsData] = useState<CustomOrNotAppointmentsEvents>({ areCustomAppointmentsEnabled: false, events: [] });
    const [appointmentDurationMinutes, setAppointmentDurationMinutes] = useState<number | null>(30);
    const [price, setPrice] = useState<number | null>(null);
    const [cathegory, setCathegory] = useState<ServiceCathegory | null>(null);

    useEffect(() => {
        async function loadEnterpriseData(): Promise<void> {
            const result = await errorErrResultAsyncFromPromise(enterprisesApi.getEnterprise(enterpriseId));
            if (result.isOk()) {
                setEnterpriseName(result.value.name);
            } else {
                navigate(routes.mainPage, { replace: true });
            }
        }
        loadEnterpriseData();
    }, [])

    const onCreateServiceClick = async () => {
        if (serviceName === "") {
            toastError("Choose a service name");
            return;
        }

        if (serviceDescription === "") {
            toastError("Enter a description");
            return;
        }

        if (price === null) {
            toastError("Set a price");
            return;
        }

        if (timeZone === null) {
            toastError("Choose a time zone");
            return;
        }

        if (cathegory === null) {
            toastError("Choose a cathegory");
            return;
        }

        if (address === null || position === null) {
            toastError("Choose a location");
            return;
        }

        const location: Location = {
            address,
            longitude: position.longitude,
            latitude: position.latitude,
        };

        let promise;
        if (eventsData.areCustomAppointmentsEnabled) {
            const timeWindowsResult = eventsData.events.map(event => eventWithIdToTimeWindow(event));
            const timeWindows = combine(timeWindowsResult);
            if (timeWindows.isErr()) {
                toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
                return;
            }

            promise = servicesApi.createCustomAppointmentsEnterpriseService(enterpriseId, {
                name: serviceName,
                description: serviceDescription,
                location,
                timeZone,
                maxDistanceKm: MAX_DISANCE_KM,
                cathegory,
                price: price,
                timeWindows: timeWindows.value,
                currency: "PLN",
            });
        } else {
            const slotsResult = eventsData.events.map(event => eventWithIdAndCapacityToSlot(event));
            const slots = combine(slotsResult);
            if (slots.isErr()) {
                toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
                return;
            }

            promise = servicesApi.createNoCustomAppointmentsEnterpriseService(enterpriseId, {
                name: serviceName,
                description: serviceDescription,
                location,
                timeZone,
                cathegory,
                price: price,
                slots: slots.value,
                currency: "PLN",
            });
        }
        const result = await errorErrResultAsyncFromPromise(promise);

        if (result.isErr()) {
            toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
            return;
        }
        navigate(routes.enterpriseStaff(enterpriseId));
    }

    return <Center height="100%">
        <Box width="80%" height="100%">
            <StandardPanel>
                <StandardFlex>
                    <Text textAlign="center">
                        Create a service for "{enterpriseName}"
                    </Text>

                    <StandardLabeledContainer label="Service name">
                        <StandardTextField text={serviceName} setText={setServiceName} />
                    </StandardLabeledContainer>

                    <StandardLabeledContainer label="Description">
                        <StandardTextField text={serviceDescription} setText={setServiceDescription} />
                    </StandardLabeledContainer>

                    <StandardLabeledContainer label="Service cathegory">
                        <ServiceCathegoryPicker value={cathegory} setValue={setCathegory} />
                    </StandardLabeledContainer>

                    <ServiceCreationCalendar
                        {...{
                            appointmentDurationMinutes,
                            setAppointmentDurationMinutes,
                            eventsData: eventsData,
                            setEventsData: setEventsData,
                        }}
                    />

                    <StandardLabeledContainer label="Location">
                        {/* <StandardConditionalTextField
                            option1Text="Chosen by you"
                            option2Text="Chosen by customer"
                            text={serviceLocation ?? ""}
                            setText={setServiceLocation}
                        /> */}
                        <MapLocationPicker address={address} setAddress={setAddress} position={position} setPosition={setPosition} />
                    </StandardLabeledContainer>

                    <StandardLabeledContainer label="Choose time zone">
                        <StandardTimeZonePicker value={timeZone} setValue={setTimeZone} />
                    </StandardLabeledContainer>

                    <StandardLabeledContainer label="Price (PLN)">
                        <StandardFloatInput
                            value={price}
                            setValue={setPrice}
                            precision={2}
                            step={0.01}
                            min={0}
                        />
                    </StandardLabeledContainer>
                    <StandardButton onClick={onCreateServiceClick}>
                        Create service
                    </StandardButton>
                </StandardFlex>
            </StandardPanel>
        </Box>
    </Center>;
}