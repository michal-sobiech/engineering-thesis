import { Icon, IconButton, IconButtonProps } from "@chakra-ui/react";
import { FC } from "react";
import { GoReport } from "react-icons/go";
import { Tooltip } from "react-tooltip";

export const ReportButton: FC<IconButtonProps> = (props) => {
    return <>
        <IconButton
            {...props}
            data-tooltip-id="report-tooltip"
            data-tooltip-content="Submit a report">
            <Icon as={GoReport as any} />
        </IconButton>
        <Tooltip id="report-tooltip" />
    </>;
}