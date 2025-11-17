import { AspectRatio, Center, Flex, Image, Text } from "@chakra-ui/react";
import { FC } from "react";
import { ReportEnterpriseButton } from "../../../common/report/ReportEnterpriseButton";
import { useIntParam } from "../../../hooks/useIntParam";

export interface EnterprisePublicPageHeaderProps {
    enterpriseName: string,
    imageUrl: string | undefined
}

export const EnterprisePublicPageHeader: FC<EnterprisePublicPageHeaderProps> = ({ enterpriseName, imageUrl }) => {
    const enterpriseId = useIntParam("enterpriseId");

    return <Flex direction="row">
        <AspectRatio ratio={1} width="100px">
            <Image src={imageUrl} />
        </AspectRatio>
        <Center flex="1" width="100%">
            <Text fontSize="5xl">{enterpriseName}</Text>
        </Center>
        <Flex direction="column">
            <ReportEnterpriseButton enterpriseId={enterpriseId} />
        </Flex>
    </Flex>;
}