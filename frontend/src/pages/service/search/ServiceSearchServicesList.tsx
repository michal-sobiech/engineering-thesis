import { Box } from "@chakra-ui/react";
import { FC, JSX } from "react";
import { ScrollableList } from "../../../common/ScrollableList";
import { StandardConcaveBox } from "../../../common/StandardConcaveBox";
import { StandardPanel } from "../../../common/StandardPanel";
import { ServiceSearchServiceData } from "./ServiceSearchServiceData";
import { ServicesSearchServicesListItem } from "./ServiceSearchServicesListItem";

export interface ServiceSearchServicesListProps {
    services: ServiceSearchServiceData[];
}

export const ServiceSearchServicesList: FC<ServiceSearchServicesListProps> = ({ services }) => {
    return <StandardConcaveBox minHeight="300px">
        <ScrollableList minHeight="300px" maxHeight="500px">
            {services.map(createItem)}
        </ScrollableList>
    </StandardConcaveBox>
}

function createItem(data: ServiceSearchServiceData): JSX.Element {
    return <Box padding="3px 5px 3px 5px">
        <StandardPanel>
            <ServicesSearchServicesListItem {...data} />
        </StandardPanel>
    </Box>
}