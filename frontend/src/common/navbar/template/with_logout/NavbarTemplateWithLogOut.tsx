
import { Flex, Spacer } from "@chakra-ui/react";
import { FC } from "react";
import { NavbarButton, NavbarButtonProps } from "../NavbarButton";
import { NavbarLogOutButton } from "./NavbarLogOutButton";

export interface NavbarTemplateWithLogOutProps {
    routeButtonProps: NavbarButtonProps[];
}

export const NavbarTemplateWithLogOut: FC<NavbarTemplateWithLogOutProps> = ({ routeButtonProps }) => {
    const routeButtons = routeButtonProps.map((props) => {
        return <NavbarButton {...props} />;
    });

    return <Flex
        align="center"
        justify="flex-start"
        height="100%"
        width="100%"
        backgroundColor="primary.blue"
        direction="row"
        gap="10px">

        {routeButtons}
        <Spacer />
        <NavbarLogOutButton />

    </Flex>;
}