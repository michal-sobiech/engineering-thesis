import { Input } from "@chakra-ui/react";
import React from "react";

export interface StandardNoneditableTextFieldProps {
    text: string,
    placeholder: string,
    type?: string,
}

export const StandardNoneditableTextField: React.FC<StandardNoneditableTextFieldProps> = ({ text, placeholder, type }) => {
    return <Input
        placeholder={placeholder}
        value={text}
        type={type}
        required
        readOnly
    />;
};