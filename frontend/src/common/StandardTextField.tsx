import { Input } from "@chakra-ui/react";
import React from "react";
import { VoidCallback } from "./VoidCallback";

export interface StandardTextFieldProps {
    text: string,
    setText: VoidCallback<string>,
    placeholder: string,
    type?: string,
}

export const StandardTextField: React.FC<StandardTextFieldProps> = ({ text, setText, placeholder, type }) => {
    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setText(event.target.value);
    };

    return <Input
        placeholder={placeholder}
        value={text}
        onChange={handleChange}
        type={type}
        required
    />;
};