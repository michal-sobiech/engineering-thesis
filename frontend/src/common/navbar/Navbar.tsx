import { AuthContext } from "../../auth/AuthContext";
import { useContextOrThrow } from "../../hooks/useContextOrThrow";
import { UserGroup } from "../UserGroup";
import { CustomerNavbar } from "./CustomerNavbar";
import { EmployeeNavbar } from "./EmployeeNavbar";
import { EntrepreneurNavbar } from "./EntrepreneurNavbar";
import { GuestNavbar } from "./GuestNavbar";
import { HeadAdminNavbar } from "./HeadAdminNavbar copy";
import { RegularAdminNavbar } from "./RegularAdminNavbar";

export const Navbar = () => {
    const authContextValue = useContextOrThrow(AuthContext);

    if (!authContextValue.auth.isAuthenticated) {
        return <GuestNavbar />;
    }

    const userGroup: UserGroup = authContextValue.auth.userGroup;
    switch (userGroup) {
        case "CUSTOMER": return <CustomerNavbar />;
        case "ENTREPRENEUR": return <EntrepreneurNavbar />;
        case "EMPLOYEE": return <EmployeeNavbar />;
        case "REGULAR_ADMIN": return <RegularAdminNavbar />;
        case "HEAD_ADMIN": return <HeadAdminNavbar />;
    }
}