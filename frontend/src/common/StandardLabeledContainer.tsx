import { Flex, Text } from "@chakra-ui/react";
import { FC, PropsWithChildren } from "react";

export type StandardLabeledContainerProps = PropsWithChildren & {
    label: string;
}

export const StandardLabeledContainer: FC<StandardLabeledContainerProps> = ({ children, label }) => {
    return <Flex direction="column" gap="5px">
        <Text>
            {label}
        </Text>
        {children}
    </Flex>;
}