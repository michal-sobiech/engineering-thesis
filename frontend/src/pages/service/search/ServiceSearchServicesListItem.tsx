import { Box, Flex, Image, Text } from "@chakra-ui/react";
import { FC } from "react";
import { ServiceSearchServiceData } from "./ServiceSearchServiceData";

export const ServicesSearchServicesListItem: FC<ServiceSearchServiceData> = ({ serviceName, enterpriseName, startTime, endTime, address }) => {
    const timeRangeLabel = `${startTime.toLocaleString()} - ${endTime.toLocaleString()}`;

    return < Box >
        <Flex direction="row" gap="5px">
            <Image />
            <Flex direction="column">
                <Text fontSize="lg" fontWeight="bold">
                    {serviceName}
                </Text>
                <Text>
                    {"\u{1F4CD}"} {address}
                </Text>
                <Text>
                    {timeRangeLabel}
                </Text>
                <Text>
                    {enterpriseName}
                </Text>
            </Flex>
        </Flex>
    </Box >;
}