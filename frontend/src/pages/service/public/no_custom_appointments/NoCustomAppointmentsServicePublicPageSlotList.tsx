import { Text } from "@chakra-ui/react";
import { LocalTime } from "js-joda";
import { JSX } from "react";
import { ScrollableList } from "../../../../common/ScrollableList";
import { extractHHmmTimeFromLocalTime } from "../../../../utils/date";
import { useContextOrThrow } from "../../../../utils/useContextOrThrow";
import { NoCustomAppointmentsServicePublicPageContext } from "./NoCustomAppointmentsServicePublicPageContextValue";

export const NoCustomAppointmentsServicePublicPageSlotList = () => {
    const { freeAppointmentsOnSelectedDate } = useContextOrThrow(NoCustomAppointmentsServicePublicPageContext);

    if (freeAppointmentsOnSelectedDate === null) {
        return null;
    } else if (freeAppointmentsOnSelectedDate.length === 0) {
        return <Text>
            No free slots!
        </Text>
    } else {
        const items = freeAppointmentsOnSelectedDate.map(createItem);
        return <ScrollableList>
            {items}
        </ScrollableList>
    }
}

function createItem(slot: [LocalTime, LocalTime]): JSX.Element {
    const startTime = extractHHmmTimeFromLocalTime(slot[0]);
    const endTime = extractHHmmTimeFromLocalTime(slot[1]);
    return <Text>
        {`${startTime} - ${endTime}`}
    </Text>
}