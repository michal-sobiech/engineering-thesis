
import { Box, Flex, Spacer } from "@chakra-ui/react";
import { FC } from "react";
import { NavbarButton, NavbarButtonProps } from "../NavbarButton";
import { NavbarLogOutButton } from "./NavbarLogOutButton";

export interface NavbarTemplateWithLogOutProps {
    routeButtonProps: NavbarButtonProps[];
}

export const NavbarTemplateWithLogOut: FC<NavbarTemplateWithLogOutProps> = ({ routeButtonProps }) => {
    const routeButtons = routeButtonProps.map((props) => {
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
        <Spacer />
        <NavbarLogOutButton />

    </Flex>;
}