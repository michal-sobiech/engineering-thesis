import { getTimeZones } from "@vvo/tzdb";
import { Select } from "chakra-react-select";
import { FC } from "react";
import { UseStateSetter } from "../utils/useState";

export interface StandardTimeZonePicker {
    value: string | null;
    setValue: UseStateSetter<string | null>;
}

const possibleTimeZones = getTimeZones().map(timeZone => timeZone.name);

const itemsData = possibleTimeZones.map(timeZone => ({
    value: timeZone,
    label: timeZone
}));


export const StandardTimeZonePicker: FC<StandardTimeZonePicker> = ({ value, setValue }) => {
    return <Select
        options={itemsData}
        placeholder={"Choose a time zone"}
        onChange={(option) => {
            console.log(option);
            setValue(option?.value ?? null);
        }} />;

}
