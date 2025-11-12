import { Flex } from "@chakra-ui/react";
import { FC } from "react";
import { NavbarButton, NavbarButtonProps } from "../NavbarButton";

export interface NavbarTemplateProps {
    routeButtonProps: NavbarButtonProps[];
}

export const NavbarTemplate: FC<NavbarTemplateProps> = ({ routeButtonProps: buttonProps }) => {
    const routeButtons = buttonProps.map((props) => {
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
        {/* <Spacer /> */}
    </Flex>;
}