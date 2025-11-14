import { Box, Flex, Image, Text } from "@chakra-ui/react";
import { FC } from "react";
import { useNavigateWithToastDismiss } from "../../../hooks/useNavigateWithToastDismiss";
import { routes } from "../../../router/routes";
import { ServiceSearchServiceData } from "./ServiceSearchServiceData";

export const ServicesSearchServicesListItem: FC<ServiceSearchServiceData> = ({ serviceId, serviceName, enterpriseName, address }) => {
    const navigate = useNavigateWithToastDismiss();

    const onClick = () => {
        navigate(routes.servicePublicPage(serviceId));
    }

    return <Box onClick={onClick} cursor="pointer">
        <Flex direction="row" gap="5px">
            <Image />
            <Flex direction="column">
                <Text fontSize="lg" fontWeight="bold">
                    {serviceName}
                </Text>
                <Text>
                    {enterpriseName}
                </Text>
                <Text>
                    {"\u{1F4CD}"} {address}
                </Text>
            </Flex>
        </Flex>
    </Box >;
}