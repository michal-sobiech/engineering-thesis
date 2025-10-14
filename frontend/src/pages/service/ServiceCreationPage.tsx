import { Box, Center, Text } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import { enterprisesApi } from "../../api/enterprises-api";
import { BooleanToggle } from "../../common/BooleanToggle";
import { StandardButton } from "../../common/StandardButton";
import { StandardConditionalTextField } from "../../common/StandardConditionalTextField";
import { StandardFlex } from "../../common/StandardFlex";
import { StandardFloatInput } from "../../common/StandardFloatInput";
import { StandardLabeledContainer } from "../../common/StandardLabeledContainer";
import { StandardPanel } from "../../common/StandardPanel";
import { StandardTextField } from "../../common/StandardTextField";
import { EventWithId } from "../../common/calendar/EventWithId";
import { WeeklyCalendarWithAutoDivide } from "../../common/calendar/WeeklyCalendarWithAutoDivide";
import { useIntParam } from "../../hooks/useIntParam";
import { routes } from "../../router/routes";
import { errorErrResultAsyncFromPromise } from "../../utils/result";
import { toastError } from "../../utils/toast";

export const ServiceCreationPage = () => {
    const navigate = useNavigate();
    const enterpriseId = useIntParam("enterpriseId");

    const [enterpriseName, setEnterpriseName] = useState<string>("");
    const [serviceName, setServiceName] = useState<string>("");
    const [serviceDescription, setServiceDescription] = useState<string>("");
    const [serviceLocation, setServiceLocation] = useState<string | null>(null);
    const [areCustomAppointmentsDisabled, setAreCustomAppointmentsDisabled] = useState<boolean>(true);
    const [appointmentDurationMinutes, setAppointmentDurationMinutes] = useState<number | null>(30);
    const [price, setPrice] = useState<number | null>(null);
    const [events, setEvents] = useState<EventWithId[]>([]);

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
        if (serviceName === null) {
            toastError("Choose a service name");
            return;
        }

        if (serviceDescription === null) {
            toastError("Enter a description");
            return;
        }

        // if (areCustomeAppointmentsDisabled)

        if (price === null) {
            toastError("Set a price");
            return;
        }

        enterprisesApi.createEnterpriseService(enterpriseId, {
            name: serviceName,
            description: serviceDescription,
            location: serviceLocation,
            takesCustomAppointments: !areCustomAppointmentsDisabled,
            price: price,
            // TODO parametrize
            currency: "PLN"
        })
    }

    return <Center height="100vh">
        <Box width="40vw">
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

                    <StandardLabeledContainer label="Custom appointments?">
                        <BooleanToggle
                            option1Text="No"
                            option2Text="Yes"
                            isOption1Chosen={areCustomAppointmentsDisabled}
                            setIsOption1Chosen={setAreCustomAppointmentsDisabled}
                        />
                    </StandardLabeledContainer>

                    {!areCustomAppointmentsDisabled ? null :
                        <StandardLabeledContainer label="Choose appointment length (in minutes)">
                            <StandardFloatInput value={appointmentDurationMinutes} setValue={setAppointmentDurationMinutes} min={0} precision={0} step={5} />
                        </StandardLabeledContainer>
                    }

                    <Box maxHeight="50vh" overflowY="scroll">
                        {/* <WeeklyCalendar /> */}
                        <WeeklyCalendarWithAutoDivide
                            events={events}
                            setEvents={setEvents}
                            eventDuration={{ minutes: appointmentDurationMinutes ?? undefined }}
                        />
                    </Box>

                    <StandardLabeledContainer label="Location">
                        <StandardConditionalTextField
                            option1Text="Chosen by you"
                            option2Text="Chosen by customer"
                            text={serviceLocation ?? ""}
                            setText={setServiceLocation}
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
                    <StandardButton onClick={onCreateServiceClick}>
                        Create service
                    </StandardButton>
                </StandardFlex>
            </StandardPanel>
        </Box>
    </Center>;
}