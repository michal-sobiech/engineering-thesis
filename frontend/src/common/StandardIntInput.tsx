import { Input } from "@chakra-ui/react";
import { FC } from "react";
import { NumericFormat } from "react-number-format";
import { VoidCallback } from "./VoidCallback";

export interface StandardFloatInputProps {
    value: number | null;
    setValue: VoidCallback<number | null>;
    step?: number;
    min?: number;
    max?: number;
    placeholder?: string;
}

export const StandardIntInput: FC<StandardFloatInputProps> = ({ value, setValue, step, min, max, placeholder }) => {
    const onValueChange = (value: any) => {
        setValue(value.floatValue);
    }

    return <NumericFormat
        value={value}
        onValueChange={onValueChange}
        decimalScale={0}
        step={step}
        min={min}
        max={max}
        customInput={Input}
        placeholder={placeholder}
    />;
}
