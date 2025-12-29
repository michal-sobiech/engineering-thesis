import { routes } from "../../router/routes";
import { NavbarButtonProps } from "./template/NavbarButton";
import { NavbarTemplateWithLogOut } from "./template/with_logout/NavbarTemplateWithLogOut";

export const HeadAdminNavbar = () => {
    const buttonsProps: NavbarButtonProps[] = [
        {
            text: "Service search",
            link: routes.serviceSearch,
        },
        {
            text: "Reports",
            link: routes.adminReportsPage,
        },
        {
            text: "Manage regular admins",
            link: routes.regularAdminListPage,
        }
    ];
    return <NavbarTemplateWithLogOut routeButtonProps={buttonsProps} />;
}