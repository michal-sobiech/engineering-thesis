import { Flex, Input, Text } from "@chakra-ui/react";
import LabeledTextFieldProps from "../../../common/LabeledTextFieldProps";

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
            fontSize="0.75rem"
            minHeight="0.75rem"
            textIndent="0.5em">
            {label}
        </Text>
    </Flex>

}

export default EmailField;