import { Box, Flex } from "@chakra-ui/react";
import { FC } from "react";
import { NavbarButton, NavbarButtonProps } from "../NavbarButton";

export interface NavbarTemplateProps {
    routeButtonProps: NavbarButtonProps[];
}

export const NavbarTemplate: FC<NavbarTemplateProps> = ({ routeButtonProps: buttonProps }) => {
    const routeButtons = buttonProps.map((props) => {
        return <Box flex="1">
            <NavbarButton {...props} />
        </Box>;
    });

    return <Flex
        align="center"
        height="8vh"
        width="100vw"
        backgroundColor="primary.blue"
        direction="row"
        gap="10px">
        {routeButtons}
    </Flex>;
}