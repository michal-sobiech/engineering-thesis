import { Button } from "@chakra-ui/react";
import { useLogOut } from "../../../../auth/useLogOut";
import { useNavigateWithToastDismiss } from "../../../../hooks/useNavigateWithToastDismiss";
import { routes } from "../../../../router/routes";

export const NavbarLogOutButton = () => {
    const navigate = useNavigateWithToastDismiss();
    const logOut = useLogOut();

    const onClick = () => {
        navigate(routes.mainPage);
        logOut();
    };

    return <Button background="transparent" onClick={onClick}>
        Log out
    </Button>
}