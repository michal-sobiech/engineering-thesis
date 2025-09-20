import { Textarea } from "@chakra-ui/react";
import { StandardTextFieldProps } from "./StandardTextField";

export const StandardTextArea: React.FC<StandardTextFieldProps> = ({ text, setText, placeholder }) => {
    const handleChange: React.ChangeEventHandler<HTMLTextAreaElement> = (event) => {
        setText(event.target.value);
    };

    return <Textarea
        placeholder={placeholder}
        value={text}
        onChange={handleChange}
    />;
}