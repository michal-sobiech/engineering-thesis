import { routes } from "../../router/routes";
import { NavbarButtonProps } from "./template/NavbarButton";
import { NavbarTemplateWithLogOut } from "./template/with_logout/NavbarTemplateWithLogOut";

export const RegularAdminNavbar = () => {
    const buttonsProps: NavbarButtonProps[] = [
        {
            text: "Reports",
            link: routes.adminReportsPage,
        },
        {
            text: "Service search",
            link: routes.serviceSearch,
        },
    ];
    return <NavbarTemplateWithLogOut routeButtonProps={buttonsProps} />;
}