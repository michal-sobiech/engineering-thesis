import { Text, TextProps } from "@chakra-ui/react";
import { FC } from "react";
import { useNavigate } from "react-router";

export type LinkTextProps = TextProps & {
    url: string;
}

export const LinkText: FC<LinkTextProps> = ({ children, url, ...otherProps }) => {
    const navigate = useNavigate();

    otherProps._hover = {
        ...(otherProps._hover ?? {}),
        textDecoration: "underline",
    };
    otherProps.onClick = () => navigate(url);
    otherProps.cursor = "pointer";

    return <Text {...otherProps}>
        {children}
    </Text>;
}