import { Center, CenterProps } from "@chakra-ui/react";
import { FC } from "react";

export const FullSpaceCenter: FC<CenterProps> = ({ children, ...otherProps }) => {
    return <Center height="100vh" width="100vw" {...otherProps}>
        {children}
    </Center>;
}