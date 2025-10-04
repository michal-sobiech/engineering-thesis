import { Flex } from "@chakra-ui/react";
import { FC, PropsWithChildren } from "react";

export const StandardFlex: FC<PropsWithChildren> = ({ children }) => {
    return <Flex direction="column" gap="10px">
        {children}
    </Flex>;
}