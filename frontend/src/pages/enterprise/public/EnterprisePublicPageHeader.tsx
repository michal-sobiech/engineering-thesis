import { AspectRatio, Center, Flex, Image, Text } from "@chakra-ui/react";

export const EnterprisePublicPageHeader = ({ enterpriseName }: { enterpriseName: string }) => {
    return <Flex direction="row">
        <AspectRatio ratio={1} width="100px">
            <Image src="https://picsum.photos/300" />
        </AspectRatio>
        <Center flex="1" width="100%">
            <Text fontSize="5xl">{enterpriseName}</Text>
        </Center>
    </Flex>;
}