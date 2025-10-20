import { Box, Center, Text } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import { Location } from "../../GENERATED-api";
import { enterprisesApi } from "../../api/enterprises-api";
import { MapLocationPicker } from "../../common/MapLocationPicker";
import { StandardButton } from "../../common/StandardButton";
import { StandardFlex } from "../../common/StandardFlex";
import { StandardFloatInput } from "../../common/StandardFloatInput";
import { StandardLabeledContainer } from "../../common/StandardLabeledContainer";
import { StandardPanel } from "../../common/StandardPanel";
import { StandardTextField } from "../../common/StandardTextField";
import { StandardTimeZonePicker } from "../../common/StandardTimeZonePicker";
import { EventWithId } from "../../common/calendar/EventWithId";
import { useIntParam } from "../../hooks/useIntParam";
import { routes } from "../../router/routes";
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../../utils/error";
import { combine, errorErrResultAsyncFromPromise } from "../../utils/result";
import { eventWithIdToSlot } from "../../utils/slot";
import { toastError } from "../../utils/toast";
import { ServiceCreationCalendar } from "./ServiceCreationCalendar";

export const ServiceCreationPage = () => {
    const navigate = useNavigate();
    const enterpriseId = useIntParam("enterpriseId");

    const [enterpriseName, setEnterpriseName] = useState<string>("");
    const [serviceName, setServiceName] = useState<string>("");
    const [serviceDescription, setServiceDescription] = useState<string>("");
    const [address, setAddress] = useState<string | null>(null);
    const [position, setPosition] = useState<{ latitude: number; longitude: number } | null>(null);
    const [timeZone, setTimeZone] = useState<string | null>(null);
    const [areCustomAppointmentsEnabled, setAreCustomAppointmentsEnabled] = useState<boolean>(false);
    const [appointmentDurationMinutes, setAppointmentDurationMinutes] = useState<number | null>(30);
    const [slots, setSlots] = useState<EventWithId[]>([]);
    const [price, setPrice] = useState<number | null>(null);

    useEffect(() => {
        async function loadEnterpriseData(): Promise<void> {
            const result = await errorErrResultAsyncFromPromise(enterprisesApi.getEnterprise(enterpriseId));
            if (result.isOk()) {
                setEnterpriseName(result.value.name);
            } else {
                navigate(routes.mainPage, { replace: true });
            }
        }
        // loadEnterpriseData();
    })

    const onCreateServiceClick = () => {
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

        const slotDtoResults = slots.map(slot => eventWithIdToSlot(slot));
        const slotDtos = combine(slotDtoResults);
        if (slotDtos.isErr()) {
            toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
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

        const promise = enterprisesApi.createEnterpriseService(enterpriseId, {
            name: serviceName,
            description: serviceDescription,
            location,
            takesCustomAppointments: areCustomAppointmentsEnabled,
            price: price,
            slots: slotDtos.value,
            timeZone,
            // TODO parametrize
            currency: "PLN",
        });
        const result = errorErrResultAsyncFromPromise(promise);
        // TODO navigate
    }

    return <Center height="100vh">
        <Box width="80vw" height="100%">
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

                    <ServiceCreationCalendar
                        {...{
                            areCustomAppointmentsEnabled,
                            setAreCustomAppointmentsEnabled,
                            appointmentDurationMinutes,
                            setAppointmentDurationMinutes,
                            events: slots,
                            setEvents: setSlots,
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