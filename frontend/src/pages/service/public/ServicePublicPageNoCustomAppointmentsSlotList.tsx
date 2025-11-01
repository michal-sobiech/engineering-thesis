import { Text } from "@chakra-ui/react";
import { JSX } from "react";
import { ScrollableList } from "../../../common/ScrollableList";
import { extractHHmmTimeFromDate } from "../../../utils/date";
import { useContextOrThrow } from "../../../utils/useContextOrThrow";
import { ServicePublicPageContext } from "./ServicePublicPageContext";

export const ServicePublicPageNoCustomAppointmentsSlotList = () => {
    const { freeAppointmentsOnSelectedDate } = useContextOrThrow(ServicePublicPageContext);

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

function createItem(slot: [Date, Date]): JSX.Element {
    const startTime = extractHHmmTimeFromDate(slot[0]);
    const endTime = extractHHmmTimeFromDate(slot[1]);
    return <Text>
        {`${startTime} - ${endTime}`}
    </Text>
}