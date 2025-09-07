import { VoidCallback } from "./VoidCallback";

export interface TextFieldProps {
    text: string,
    setText: VoidCallback<string>,
}