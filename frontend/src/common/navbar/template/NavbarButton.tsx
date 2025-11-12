import { Button } from "@chakra-ui/react";
import { FC } from "react";
import { useNavigateWithToastDismiss } from "../../../hooks/useNavigateWithToastDismiss";

export interface NavbarButtonProps {
    text: string;
    link: string;
}

export const NavbarButton: FC<NavbarButtonProps> = ({ text, link }) => {
    const navigate = useNavigateWithToastDismiss();

    const onClick = () => {
        navigate(link);
    }

    return <Button onClick={onClick}>
        text
    </Button>
}