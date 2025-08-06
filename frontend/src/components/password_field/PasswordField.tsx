import { Flex, Input, Text } from "@chakra-ui/react";
import PasswordFieldProps from "./props";

const PasswordField = ({ password, setPassword, label }: PasswordFieldProps) => {

    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setPassword(event.target.value);
    }

    return <Flex direction="column">
        <Input
            type="password"
            name="password"
            value={password}
            onChange={handleChange}
            placeholder="Password"
            required
        />
        <Text display="inline-block"
            fontSize="0.75rem"
            minHeight="0.75rem"
            textIndent="0.5em">
            {label}
        </Text>
    </Flex>;
}

export default PasswordField;