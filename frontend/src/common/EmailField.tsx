import { Flex, Input, Text } from "@chakra-ui/react";
import LabeledTextFieldProps from "./LabeledTextFieldProps";

const EmailField = ({ text, setText, label }: LabeledTextFieldProps) => {

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
        <Text display="inline-block"
            fontSize="xs"
            minHeight="0.75rem"
            textIndent="0.75rem">
            {label}
        </Text>
    </Flex>

}

export default EmailField;