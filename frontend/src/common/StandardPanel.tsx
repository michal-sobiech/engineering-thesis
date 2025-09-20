import { Flex } from "@chakra-ui/react";
import { ReactNode } from "react";

export const StanadardPanel = ({ children }: { children: ReactNode }) => {
    return (
        <Flex bg="white"
            p="5"
            rounded="lg"
            shadow="lg"
            direction="column"
            gap="10px">
            {children}
        </Flex>
    );
}