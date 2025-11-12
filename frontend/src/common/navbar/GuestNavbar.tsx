import { routes } from "../../router/routes";
import { NavbarButtonProps } from "./template/NavbarButton";
import { NavbarTemplate } from "./template/normal/NavbarTemplate";

export const GuestNavbar = () => {
    const buttonsProps: NavbarButtonProps[] = [
        {
            text: "Home",
            link: routes.mainPage,
        },
        {
            text: "Sign up",
            link: routes.mainPage,
        },
        {
            text: "Log in",
            link: routes.mainPage,
        },
        {
            text: "Service search",
            link: routes.serviceSearch,
        },
    ];
    return <NavbarTemplate routeButtonProps={buttonsProps} />;
}