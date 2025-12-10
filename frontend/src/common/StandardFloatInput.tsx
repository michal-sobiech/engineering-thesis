import { Input } from "@chakra-ui/react";
import { FC } from "react";
import { NumericFormat } from "react-number-format";

export interface StandardFloatInputProps {
    value: number | null;
    setValue: (value: number) => void;
    precision?: number;
    step?: number;
    min?: number;
    max?: number;
}

export const StandardFloatInput: FC<StandardFloatInputProps> = ({ value, setValue, precision, step, min, max }) => {
    const onValueChange = (value: any) => {
        setValue(value.floatValue);
    }

    return <NumericFormat
        value={value}
        onValueChange={onValueChange}
        decimalScale={precision}
        step={step}
        min={min}
        max={max}
        customInput={Input}
    />;
}