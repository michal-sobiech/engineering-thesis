import { Select } from "chakra-react-select";
import { FC } from "react";
import { typedEntries } from "../../utils/record";
import { UseStateSetter } from "../../utils/use-state";
import { ServiceCathegory, serviceCathegoryLabels } from "./ServiceCathegory";

export interface ServiceCathegoryPickerProps {
    value: ServiceCathegory | null;
    setValue: UseStateSetter<ServiceCathegory | null>;
}

const options = typedEntries(serviceCathegoryLabels).map(([key, value]) => ({ label: value, value: key }));

export const ServiceCathegoryPicker: FC<ServiceCathegoryPickerProps> = ({ value, setValue }) => {
    return <Select
        options={options}
        placeholder="-"
        onChange={(option) => setValue(option?.value ?? null)}
    />;
}