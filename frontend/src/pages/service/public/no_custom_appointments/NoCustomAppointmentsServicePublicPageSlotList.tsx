import { Text } from "@chakra-ui/react";
import { LocalTime } from "@js-joda/core";
import { JSX } from "react";
import { ScrollableList } from "../../../../common/ScrollableList";
import { StandardConcaveBox } from "../../../../common/StandardConcaveBox";
import { useContextOrThrow } from "../../../../hooks/useContextOrThrow";
import { sameElements } from "../../../../utils/array";
import { extractHHmmTimeFromLocalTime } from "../../../../utils/date";
import { NoCustomAppointmentsServicePublicPageContext } from "./NoCustomAppointmentsServicePublicPageContextValue";

export const NoCustomAppointmentsServicePublicPageSlotList = () => {
    const { freeSlotsOnSelectedDate, selectedSlot, setSelectedSlot } = useContextOrThrow(NoCustomAppointmentsServicePublicPageContext);

    function createItem(slot: [LocalTime, LocalTime]): JSX.Element {
        const startTime = extractHHmmTimeFromLocalTime(slot[0]);
        const endTime = extractHHmmTimeFromLocalTime(slot[1]);

        const onClick = () => {
            setSelectedSlot(slot);
        }

        const isChosen = selectedSlot && (sameElements(slot, selectedSlot));
        const fontWeight = isChosen ? "bold" : "normal";

        return <Text cursor="pointer" onClick={onClick} fontWeight={fontWeight}>
            {`${startTime} - ${endTime}`}
        </Text>
    }

    if (freeSlotsOnSelectedDate === null) {
        return null;
    }

    let content;
    if (freeSlotsOnSelectedDate.length === 0) {
        content = <Text>
            No free slots!
        </Text>;
    } else {
        const items = freeSlotsOnSelectedDate.map(createItem);
        content = <ScrollableList>
            {items}
        </ScrollableList>;
    }

    return <StandardConcaveBox padding="3%">
        {content}
    </StandardConcaveBox>;
}
