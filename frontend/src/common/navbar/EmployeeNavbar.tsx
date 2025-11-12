import { routes } from "../../router/routes";
import { NavbarButtonProps } from "./template/NavbarButton";
import { NavbarTemplateWithLogOut } from "./template/with_logout/NavbarTemplateWithLogOut";

export const EmployeeNavbar = () => {
    const buttonsProps: NavbarButtonProps[] = [
        {
            text: "Home",
            link: routes.mainPage,
        },
        {
            text: "Service search",
            link: routes.serviceSearch,
        },
        {
            text: "My enterprise",
            // TODO
            link: routes.mainPage,
        },
    ];
    return <NavbarTemplateWithLogOut routeButtonProps={buttonsProps} />;
}