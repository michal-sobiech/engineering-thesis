import { Flex, Input } from "@chakra-ui/react";
import { TextFieldProps } from "../TextFieldProps";

export const IndependentEndUserLogInPasswordField = ({ text, setText }: TextFieldProps) => {

    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setText(event.target.value);
    };

    return <Flex direction="column">
        <Input
            type="password"
            name="password"
            placeholder="Password"
            value={text}
            onChange={handleChange}
            required
        />
    </Flex>

}