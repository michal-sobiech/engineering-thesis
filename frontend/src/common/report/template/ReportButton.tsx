import { Icon, IconButton } from "@chakra-ui/react";
import { FC, JSX, useState } from "react";
import { GoReport } from "react-icons/go";
import { Tooltip } from "react-tooltip";
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../../../utils/error";
import { Position } from "../../../utils/Position";
import { errorErrResultAsyncFromPromise } from "../../../utils/result";
import { toastError, toastSuccess } from "../../../utils/toast";
import { ReportPopup } from "./ReportPopup";

export interface ReportButtonProps {
    submitReport: (text: string) => Promise<void>;
}

export const ReportButton: FC<ReportButtonProps> = ({ submitReport }) => {
    const [popup, setPopup] = useState<JSX.Element | null>();

    const submitReportWrapper = async (text: string) => {
        const promise = submitReport(text);
        const result = await errorErrResultAsyncFromPromise(promise);
        if (result.isErr()) {
            toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
            return;
        }
        toastSuccess("Reported review!");
    }

    const onClick = (event: React.MouseEvent<HTMLButtonElement>) => {
        const clickPosition: Position = {
            x: event.clientX,
            y: event.clientY
        };

        const closePopup = () => {
            setPopup(null);
        }

        let newPopup = <ReportPopup submitReport={submitReportWrapper} position={clickPosition} close={closePopup} />;
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