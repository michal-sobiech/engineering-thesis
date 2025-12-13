import { Box, Center, Flex } from "@chakra-ui/react"
import { useEffect, useState } from "react"
import { useNavigate } from "react-router"
import { useServicesApi } from "../../../../api/services-api"
import { swaggerSlotToWeeklyTimeWindowWithCapacity, weeklyTimeWindowWithCapacityToSwaggerSlot } from "../../../../api/slot-mapper"
import { EditableNonCustomWeeklySchedule } from "../../../../common/calendar/weekly/editable/EditableNonCustomWeeklySchedule"
import { useServiceIdFromLoader } from "../../../../common/loader/service-id-loader"
import { MapLocationPicker } from "../../../../common/MapLocationPicker"
import { StandardButton } from "../../../../common/StandardButton"
import { StandardFloatInput } from "../../../../common/StandardFloatInput"
import { StandardLabeledContainer } from "../../../../common/StandardLabeledContainer"
import { StandardPanel } from "../../../../common/StandardPanel"
import { StandardTextField } from "../../../../common/StandardTextField"
import { StandardTimeZonePicker } from "../../../../common/StandardTimeZonePicker"
import { StandardVerticalSeparator } from "../../../../common/StandardVerticalSeparator"
import { Location, Slot as SwaggerSlot } from "../../../../GENERATED-api"
import { routes } from "../../../../router/routes"
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../../../../utils/error"
import { GeoPosition } from "../../../../utils/GeoPosition"
import { toastError } from "../../../../utils/toast"
import { WeeklyTimeWindowWithCapacity } from "../../../../utils/WeeklyTimeWindowWithCapacity"
import { isServiceCathegory, ServiceCathegory } from "../../ServiceCathegory"
import { ServiceCathegoryPicker } from "../../ServiceCathegoryPicker"
import { StaffNonCustomServicePageContext, StaffNonCustomServicePageContextValue } from "./StaffNonCustomServicePageContext"

export const StaffNonCustomServicePage = () => {
    const serviceId = useServiceIdFromLoader();
    const navigate = useNavigate();
    const servicesApi = useServicesApi();

    const [enterpriseId, setEnterpriseId] = useState<number | null>(null);
    const [enterpriseName, setEnterpriseName] = useState<string>("");
    const [serviceName, setServiceName] = useState<string>("");
    const [serviceDescription, setServiceDescription] = useState<string>("");
    const [address, setAddress] = useState<string | null>(null);
    const [position, setPosition] = useState<GeoPosition | null>(null);
    const [timezone, setTimezone] = useState<string | null>(null);
    const [windows, setWindows] = useState<WeeklyTimeWindowWithCapacity[]>([]);
    const [appointmentDurationMinutes, setAppointmentDurationMinutes] = useState<number | null>(30);
    const [price, setPrice] = useState<number | null>(null);
    const [cathegory, setCathegory] = useState<ServiceCathegory | null>(null);

    const contextValue: StaffNonCustomServicePageContextValue = {
        enterpriseName, setEnterpriseName,
        serviceName, setServiceName,
        serviceDescription, setServiceDescription,
        address, setAddress,
        position, setPosition,
        timezone, setTimezone,
        windows, setWindows,
        appointmentDurationMinutes, setAppointmentDurationMinutes,
        price, setPrice,
        cathegory, setCathegory,
    };

    useEffect(() => {
        servicesApi.getNonCustomEnterpriseService(serviceId)
            .then(response => {
                setEnterpriseId(response.enterpriseId);
                setEnterpriseName(response.enterpriseName);
                setServiceName(response.name);
                setServiceDescription(response.description);
                setAddress(response.location.address);
                setPosition({
                    longitude: response.location.longitude,
                    latitude: response.location.latitude,
                });

                const windows: WeeklyTimeWindowWithCapacity[] = response.slotTemplates
                    .map(swaggerSlotToWeeklyTimeWindowWithCapacity);
                setWindows(windows);

                setTimezone(response.timezone);
                setPrice(response.price);

                if (isServiceCathegory(response.cathegory)) {
                    setCathegory(response.cathegory);
                } else {
                    throw new Error("Value is not a ServiceCathegory");
                }
            })
            .catch(() => {
                toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
                navigate(routes.mainPage);
            });
    }, []);

    const onDicardClick = () => {
        if (enterpriseId !== null) {
            navigate(routes.enterpriseStaff(enterpriseId));
        } else {
            navigate(routes.mainPage);
        }
    }

    const onSaveClick = () => {
        let location: Location | undefined = undefined;
        if (address !== null && position !== null) {
            location = {
                address,
                longitude: position.longitude,
                latitude: position.latitude
            };
        }

        const slots: SwaggerSlot[] = windows
            .map(weeklyTimeWindowWithCapacityToSwaggerSlot);

        servicesApi.patchNonCustomEnterpriseService(serviceId, {
            name: serviceName,
            description: serviceDescription,
            location,
            timeZone: timezone ?? undefined,
            cathegory: cathegory ?? undefined,
            price: price ?? undefined,
            slots,
        }).catch(() => {
            toastError("Couldn't edit service. Try again later");
        });
        if (enterpriseId !== null) {
            navigate(routes.enterpriseStaff(enterpriseId));
        } else {
            navigate(routes.mainPage);
        }
    }

    return <StaffNonCustomServicePageContext.Provider value={contextValue}>
        <Center height="100%">
            <StandardPanel width="80%" height="100%" padding="20px" overflowY="scroll">
                <Flex direction="column" height="100%" minHeight={0} gap="10px">

                    <StandardLabeledContainer label="Enterprise name">
                        <StandardTextField text={enterpriseName} setText={() => { }} disabled />
                    </StandardLabeledContainer>

                    <StandardLabeledContainer label="Service name">
                        <StandardTextField text={serviceName} setText={setServiceName} />
                    </StandardLabeledContainer>

                    <StandardLabeledContainer label="Service description">
                        <StandardTextField text={serviceDescription} setText={setServiceDescription} />
                    </StandardLabeledContainer>

                    <StandardLabeledContainer label="Location">
                        <StandardTextField text={address ?? ""} setText={() => { }} disabled />
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

                    <StandardLabeledContainer label="Appointment duration (minutes)">
                        <StandardFloatInput
                            value={appointmentDurationMinutes}
                            setValue={setAppointmentDurationMinutes}
                            min={0}
                            precision={0}
                            step={5} />
                    </StandardLabeledContainer>

                    <StandardLabeledContainer label="Schedule">
                        <EditableNonCustomWeeklySchedule
                            windows={windows}
                            setWindows={setWindows}
                            appointmentDurationMinutes={appointmentDurationMinutes}
                        />
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

                    <StandardLabeledContainer label="Service cathegory">
                        <ServiceCathegoryPicker value={cathegory} setValue={setCathegory} />
                    </StandardLabeledContainer>

                    <StandardVerticalSeparator />

                    <Box minHeight="5px" />
                    <Flex direction="row" gap="5px">
                        <StandardButton
                            onClick={onDicardClick}
                            flex="1"
                            backgroundColor="primary.darkRed">
                            Discard
                        </StandardButton>
                        <StandardButton
                            onClick={onSaveClick}
                            flex="1"
                            backgroundColor="primary.lightGreen">
                            Save
                        </StandardButton>
                    </Flex>

                    <StandardVerticalSeparator />

                </Flex>
            </StandardPanel>
        </Center >
    </StaffNonCustomServicePageContext.Provider>

}