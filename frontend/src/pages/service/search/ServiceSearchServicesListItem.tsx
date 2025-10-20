import { Box, Flex, Image, Text } from "@chakra-ui/react";
import { FC } from "react";

export interface ServicesSearchServicesListItemProps {
    serviceName: string;
    enterpriseName: string;
    time: Date;
    // Duration: 
}

export const ServicesSearchServicesListItem: FC<ServicesSearchServicesListItemProps> = ({ serviceName, time, enterpriseName }) => {
    // const timeLabel = time.

    return < Box >
        <Flex direction="row" gap="5px">
            <Image />
            <Flex direction="column">
                <Text fontSize="lg" fontWeight="bold">
                    {serviceName}
                </Text>
                <Text>
                    Godzina
                </Text>
                <Text>
                    Enterprise "..."
                </Text>
            </Flex>
        </Flex>
    </Box >;
}