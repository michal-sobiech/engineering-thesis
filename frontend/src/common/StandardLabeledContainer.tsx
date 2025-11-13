import { Flex, FlexProps, Text } from "@chakra-ui/react";
import { FC } from "react";

export type StandardLabeledContainerProps = FlexProps & {
    label: string;
}

export const StandardLabeledContainer: FC<StandardLabeledContainerProps> = ({ children, label, ...otherProps }) => {
    return <Flex
        direction="column"
        gap="5px"
        {...otherProps}>
        <Text>
            {label}
        </Text>
        {children}
    </Flex>;
}