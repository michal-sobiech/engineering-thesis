import { Icon, IconButton } from "@chakra-ui/react";
import { FC, JSX, useState } from "react";
import { GoReport } from "react-icons/go";
import { Tooltip } from "react-tooltip";
import { Position } from "../../../utils/Position";
import { ReportPopup } from "./ReportPopup";

export interface ReportButtonProps {
    submitReport: (text: string) => Promise<void>;
}

export const ReportButton: FC<ReportButtonProps> = ({ submitReport }) => {
    const [popup, setPopup] = useState<JSX.Element | null>();

    const onClick = (event: React.MouseEvent<HTMLButtonElement>) => {
        const clickPosition: Position = {
            x: event.clientX,
            y: event.clientY
        };

        const closePopup = () => {
            setPopup(null);
        }

        let newPopup = <ReportPopup submitReport={submitReport} position={clickPosition} close={closePopup} />;
        setPopup(newPopup);
    };

    return <>
        <IconButton
            onClick={onClick}
            data-tooltip-id="report-tooltip"
            data-tooltip-content="Submit a report">
            <Icon as={GoReport as any} />
        </IconButton>
        <Tooltip id="report-tooltip" />
        {popup}
    </>;
}