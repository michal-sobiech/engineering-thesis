import { Flex, Input, Text } from "@chakra-ui/react";
import UsernameFieldProps from "./props";

const UsernameField = ({ username, setUsername, label }: UsernameFieldProps) => {

    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setUsername(event.target.value);
    };

    return <Flex direction="column">
        <Input
            type="text"
            name="username"
            placeholder="Username"
            value={username}
            onChange={handleChange}
            required
        />
        <Text display="inline-block"
            fontSize="0.75rem"
            minHeight="0.75rem"
            textIndent="0.5em">
            {label}
        </Text>
    </Flex>

}

export default UsernameField;