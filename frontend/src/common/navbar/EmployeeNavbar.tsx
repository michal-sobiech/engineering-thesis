import { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import { useEnterpriseEmployeesApi } from "../../api/enterprise-employees-api";
import { routes } from "../../router/routes";
import { NavbarButtonProps } from "./template/NavbarButton";
import { NavbarTemplateWithLogOut } from "./template/with_logout/NavbarTemplateWithLogOut";

export const EmployeeNavbar = () => {
    const employeesApi = useEnterpriseEmployeesApi();
    const navigate = useNavigate();

    const [enterpriseId, setEnterpriseId] = useState<number | null>(null);

    useEffect(() => {
        employeesApi.getMeEmployee()
            .then(response => {
                setEnterpriseId(response.enterpriseId);
            }).catch(() => {
                navigate(routes.mainPage);
            });
    });

    const myEnterpriseLink = enterpriseId === null
        ? routes.mainPage
        : routes.employeeLandingPage;

    console.log(myEnterpriseLink);

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
            link: myEnterpriseLink,
        },
    ];
    return <NavbarTemplateWithLogOut routeButtonProps={buttonsProps} />;
}