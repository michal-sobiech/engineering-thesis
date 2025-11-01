import { Text } from "@chakra-ui/react"
import { FC } from "react"
import { extractHHmmTimeFromDate } from "../../../utils/date"
import { useContextOrThrow } from "../../../utils/useContextOrThrow"
import { ServicePublicPageContext } from "./ServicePublicPageContext"

export interface ServicePublicPageNoCustomAppointmentsSlotListProps {
    start: Date,
    end: Date
}

export const ServicePublicPageNoCustomAppointmentsSlotList: FC<ServicePublicPageNoCustomAppointmentsSlotListProps> = ({ start, end }) => {
    const { selectedAppointment, setSelectedAppointment } = useContextOrThrow(ServicePublicPageContext);

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