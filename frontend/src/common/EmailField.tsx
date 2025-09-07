import { Flex, Input } from "@chakra-ui/react";
import React from "react";
import { TextFieldProps } from "./TextFieldProps";

const EmailField: React.FC<TextFieldProps> = ({ text, setText }) => {

    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setText(event.target.value);
    };

    return <Flex direction="column">
        <Input
            type="email"
            name="email"
            placeholder="E-mail address"
            value={text}
            onChange={handleChange}
            required
        />
    </Flex>

}

export default EmailField;