import { Heading, Text } from "@chakra-ui/react";
import { DateTimeFormatter, LocalDateTime } from "@js-joda/core";
import { JSX, useEffect, useState } from "react";
import { appointmentsApi } from "../../../api/appointments-api";
import { ScrollableList } from "../../../common/ScrollableList";
import { StandardButton } from "../../../common/StandardButton";
import { StandardConcaveBox } from "../../../common/StandardConcaveBox";
import { StandardFlex } from "../../../common/StandardFlex";
import { StandardLabeledContainer } from "../../../common/StandardLabeledContainer";
import { StandardPanel } from "../../../common/StandardPanel";
import { errorErrResultAsyncFromPromise } from "../../../utils/result";
import { CustomerLandingPageScheduledAppointment } from "./CustomerLandingPageScheduledAppointment";

export const CustomerLandingPagePastScheduledAppointments = () => {
    const [appointments, setAppointments] = useState<CustomerLandingPageScheduledAppointment[]>([]);

    useEffect(() => {
        async function loadAppointments() {
            const promise = appointmentsApi.getMyPastScheduledAppointments();
            const asyncResult = errorErrResultAsyncFromPromise(promise);
            const result = await asyncResult;
            if (result.isErr()) {
                return;
            }
            const mapped: CustomerLandingPageScheduledAppointment[] = result.value.map(item => ({
                serviceName: item.serviceName,
                enterpriseName: item.enterpriseName,
                address: item.address,
                startDatetimeServiceLocal: LocalDateTime.parse(item.startDatetimeServiceLocal),
                endDatetimeServiceLocal: LocalDateTime.parse(item.endDatetimeServiceLocal),
                timezone: item.timezone,
                price: item.price
            }));
            setAppointments(mapped);
        }
        loadAppointments();
    }, []);

    return <StandardLabeledContainer label="Past appointments">
        <StandardConcaveBox>
            <ScrollableList>
                {appointments === null ? null : appointments.map(createItem)}
            </ScrollableList>
        </StandardConcaveBox>
    </StandardLabeledContainer>
}

function createItem(data: CustomerLandingPageScheduledAppointment): JSX.Element {
    const date = data.startDatetimeServiceLocal.toLocalDate();
    const dateFormatted = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

    const startFormatted = data.startDatetimeServiceLocal.format(DateTimeFormatter.ofPattern("HH:mm"));
    const endFormatted = data.endDatetimeServiceLocal.format(DateTimeFormatter.ofPattern("HH:mm"));

    return <StandardPanel>
        <StandardFlex>
            <Heading>
                {data.serviceName}
            </Heading>
            <Text>
                {"\u{1F4CD}"} {data.address}
            </Text>
            <Text>
                {dateFormatted} {startFormatted} - {endFormatted}
            </Text>
            <StandardButton backgroundColor="primary.darkRed">
                Cancel
            </StandardButton>
        </StandardFlex>
    </StandardPanel>;
}