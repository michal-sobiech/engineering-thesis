import { Flex, FlexProps } from "@chakra-ui/react";
import { FC, PropsWithChildren } from "react";

export const StandardFlex: FC<FlexProps & PropsWithChildren> = ({ children, ...otherProps }) => {
    return <Flex direction="column" gap="10px" {...otherProps}>
        {children}
    </Flex>;
}