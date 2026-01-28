import { Text } from "@chakra-ui/react"
import { FC } from "react"
import { useContextOrThrow } from "../../../../hooks/useContextOrThrow"
import { extractHHmmTimeFromDate } from "../../../../utils/date"
import { NoCustomAppointmentsServicePublicPageContext } from "./NoCustomAppointmentsServicePublicPageContextValue"

export interface NoCustomAppointmentsServicePublicPageSlotListProps {
    start: Date,
    end: Date
}

export const ServicePublicPageNoCustomAppointmentsSlotList: FC<NoCustomAppointmentsServicePublicPageSlotListProps> = ({ start, end }) => {
    const { selectedSlot: selectedAppointment, setSelectedSlot: setSelectedAppointment } = useContextOrThrow(NoCustomAppointmentsServicePublicPageContext);

    const onClick = () => {
    }

    const startTimeString = extractHHmmTimeFromDate(start);
    const endTimeString = extractHHmmTimeFromDate(end);

    return <Text cursor="pointer" onClick={onClick}>
        {`${startTimeString} - ${endTimeString}`}
    </Text>
}