import { Text } from "@chakra-ui/react"
import { FC } from "react"
import { extractHHmmTimeFromDate } from "../../../../utils/date"
import { useContextOrThrow } from "../../../../utils/useContextOrThrow"
import { NoCustomAppointmentsServicePublicPageContext } from "./NoCustomAppointmentsServicePublicPageContextValue"

export interface NoCustomAppointmentsServicePublicPageSlotListProps {
    start: Date,
    end: Date
}

export const ServicePublicPageNoCustomAppointmentsSlotList: FC<NoCustomAppointmentsServicePublicPageSlotListProps> = ({ start, end }) => {
    const { selectedAppointment, setSelectedAppointment } = useContextOrThrow(NoCustomAppointmentsServicePublicPageContext);

    const onClick = () => {
        setSelectedAppointment([start, end]);
        console.log("1233231")
    }

    const startTimeString = extractHHmmTimeFromDate(start);
    const endTimeString = extractHHmmTimeFromDate(end);

    return <Text cursor="pointer" onClick={onClick}>
        {`${startTimeString} - ${endTimeString}`}
    </Text>
}